package se.fk.rimfrost.referensdata.erbjudande;

import io.quarkus.test.InjectMock;
import io.quarkus.test.component.QuarkusComponentTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import se.fk.rimfrost.adapter.referensdata.adapter.ReferensdataAdapter;
import se.fk.rimfrost.adapter.referensdata.adapter.ReferensdataException;
import se.fk.rimfrost.adapter.referensdata.model.ImmutableReferensdata;
import se.fk.rimfrost.adapter.referensdata.model.Referensdata;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusComponentTest
public class ErbjudandeReferensdataTest
{
   @Inject
   ErbjudandeReferensdata erbjudandeReferensdata;

   @InjectMock
   ReferensdataAdapter referensdataAdapter;

   @ParameterizedTest
   @CsvSource(
   {
         "Erbjudande1",
         "Erbjudande2"
   })
   public void should_return_namn_on_success(String erbjudandeNamn) throws ReferensdataException
   {
      String erbjudandeId = UUID.randomUUID().toString();
      Mockito.when(referensdataAdapter.getErbjudande(erbjudandeId)).thenReturn(createReferensdata(erbjudandeId, erbjudandeNamn));
      assertEquals(erbjudandeNamn, erbjudandeReferensdata.getErbjudandeNamn(erbjudandeId));
   }

   @Test
   public void should_throw_on_referensdata_exception() throws ReferensdataException
   {
      String erbjudandeId = UUID.randomUUID().toString();
      Mockito.when(referensdataAdapter.getErbjudande(erbjudandeId)).thenThrow(ReferensdataException.class);
      assertThrows(RuntimeException.class, () -> {
         erbjudandeReferensdata.getErbjudandeNamn(erbjudandeId);
      });
   }

   @Test
   public void should_throw_on_null_value_response() throws ReferensdataException
   {
      String erbjudandeId = UUID.randomUUID().toString();
      Mockito.when(referensdataAdapter.getErbjudande(erbjudandeId)).thenReturn(null);
      assertThrows(RuntimeException.class, () -> {
         erbjudandeReferensdata.getErbjudandeNamn(erbjudandeId);
      });
   }

   private Referensdata createReferensdata(String id, String namn)
   {
      return ImmutableReferensdata.builder()
            .id(id)
            .kod("1234")
            .namn(namn)
            .build();
   }
}
