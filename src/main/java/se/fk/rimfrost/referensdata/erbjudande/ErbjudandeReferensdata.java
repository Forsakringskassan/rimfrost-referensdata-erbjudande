package se.fk.rimfrost.referensdata.erbjudande;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import se.fk.rimfrost.adapter.referensdata.adapter.ReferensdataAdapter;
import se.fk.rimfrost.adapter.referensdata.adapter.ReferensdataException;
import se.fk.rimfrost.framework.referensdata.ErbjudandeReferensdataInterface;

@ApplicationScoped
public class ErbjudandeReferensdata implements ErbjudandeReferensdataInterface
{
   @Inject
   ReferensdataAdapter referensdataAdapter;

   @Override
   public String getErbjudandeNamn(String id)
   {
      try
      {
         return referensdataAdapter.getErbjudande(id).namn();
      }
      catch (ReferensdataException e)
      {
         throw new RuntimeException(e);
      }

   }
}
