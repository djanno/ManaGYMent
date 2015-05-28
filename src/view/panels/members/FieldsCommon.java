package view.panels.members;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * @author Davide Borficchia
 * 
 **/

public class FieldsCommon implements IFormStrategy{
	
	public enum EnumFieldsCommon implements IFormField{
		
		NOME("Nome",  x -> x.length() > 1), 
		COGNOME("Cognome", x -> x.length() > 1),
		CODICE_FISCALE("Codice fiscale", x -> x.length() == 15), 
		INDIRIZZO("Indirizzo", x -> x.length() > 7), 
		TELEFONO("Telefono", x -> isNumber(x)),
		EMAIL("E-Mail", x -> Pattern.compile(EnumFieldsCommon.EMAIL_PATTERN).matcher(x).matches()); 
		
		private static final String EMAIL_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		
		private String fieldName;
		private Predicate<String> pred;
		
		private EnumFieldsCommon(final String nomeCampo, final Predicate<String> pred){
			this.fieldName = nomeCampo;
			this.pred = pred;
		}

		@Override
		public String getField() {
			return this.fieldName;
		}
		
		@Override
		public Predicate<String> getPred() {
			return this.pred;
		}
		
		/**
		 * @param s
		 * 		the value to be checked
		 * @return
		 */
		private static boolean isNumber(String s){
			try{
				return Optional.ofNullable(new Long(s)).isPresent();//ritrona true se è riuscito a convertirlo, altrimenti ritorna false
			} catch (NumberFormatException e){
				return false;
			}
		}
	}

	@Override
	public List<IFormField> getFields() {
		return Arrays.asList(EnumFieldsCommon.values());
	}
}
