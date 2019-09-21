package offers.utils;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.collect.Lists;

import offers.domain.Offer;
import offers.domain.OfferStatus;
import offers.repository.OfferRepository;

public class OfferStatusUtilsTest {
	
	@Mock
	private OfferRepository offerRepository;
	
	private OfferStatusUtils offerStatusUtils;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		offerStatusUtils = spy(new OfferStatusUtils(offerRepository));
	}

	@Test
	public void updateOfferStatusesValidTest() {
		LocalDateTime currentDate = LocalDateTime.now();
		Offer validOffer = new Offer(Lists.newArrayList(), "test valid offer", 1.99, 
				currentDate.minusDays(1),
				currentDate.plusDays(1));
		doReturn(Lists.newArrayList(validOffer)).when(offerRepository).findAll();
		
		ArgumentCaptor<Offer> offerCaptor = ArgumentCaptor.forClass(Offer.class);	
		offerStatusUtils.updateOfferStatuses();
		
		verify(offerRepository, times(1)).save(offerCaptor.capture());
		assertEquals("Offer should be valid", OfferStatus.VALID, offerCaptor.getValue().getStatus());		
	}
	
	@Test
	public void updateOfferStatusesExpiredTest() {
		LocalDateTime currentDate = LocalDateTime.now();
		Offer validOffer = new Offer(Lists.newArrayList(), "test expired offer", 2.50, 
				currentDate.minusDays(5),
				currentDate.minusDays(1));
		doReturn(Lists.newArrayList(validOffer)).when(offerRepository).findAll();
		
		ArgumentCaptor<Offer> offerCaptor = ArgumentCaptor.forClass(Offer.class);	
		offerStatusUtils.updateOfferStatuses();
		
		verify(offerRepository, times(1)).save(offerCaptor.capture());
		assertEquals("Offer should be expired", OfferStatus.EXPIRED, offerCaptor.getValue().getStatus());		
	}
	
	@Test
	public void updateOfferStatusesPendingTest() {
		LocalDateTime currentDate = LocalDateTime.now();
		Offer validOffer = new Offer(Lists.newArrayList(), "test pending offer", 0.12, 
				currentDate.plusDays(1),
				currentDate.plusDays(8));
		doReturn(Lists.newArrayList(validOffer)).when(offerRepository).findAll();
		
		ArgumentCaptor<Offer> offerCaptor = ArgumentCaptor.forClass(Offer.class);	
		offerStatusUtils.updateOfferStatuses();
		
		verify(offerRepository, times(1)).save(offerCaptor.capture());
		assertEquals("Offer should be pending", OfferStatus.PENDING, offerCaptor.getValue().getStatus());		
	}
	
	@Test
	public void updateOfferStatusesCancelledTest() {
		LocalDateTime currentDate = LocalDateTime.now();
		Offer cancelledOffer = new Offer(Lists.newArrayList(), "test cancelled offer", 0.12, 
				currentDate.plusDays(1),
				currentDate.plusDays(8));
		cancelledOffer.setStatus(OfferStatus.CANCELLED);
		doReturn(Lists.newArrayList(cancelledOffer)).when(offerRepository).findAll();
		
		verify(offerRepository, times(0)).save(any(Offer.class));
	}

}
