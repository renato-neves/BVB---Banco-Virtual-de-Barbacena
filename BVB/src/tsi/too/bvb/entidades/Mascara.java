package tsi.too.bvb.entidades;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

/** A classe <code>Mascara</code> manipula mascaras para os campos de texto
 * 
 * @author Gian Carlos Barros Hon�rio
 * @author Diego Oliveira
 *
 * @see MaskFormatter
 */
public class Mascara extends MaskFormatter {
	
	private static final long serialVersionUID = 8289201161597596230L;

	/** Cria uma <code>Mascara</code> com o formato especificado, por exemplo ###-###
	 *  @param formatoMascara <code>String</code> com o formato da mascara
	 */
	public Mascara(String formatoMascara) { //exemplo: ###-### ou ####-####
	       try{
	    	   this.setMask(formatoMascara); //Atribui a mascara
	    	   this.setPlaceholderCharacter(' '); //Caracter para preenchimento
	       }
	       catch (Exception excecao) {
	    	   excecao.printStackTrace();
	       }
	}
	
	/** Cria uma <code>Mascara</code> com o formato especificado e um caracter para mostrar o espa�o vazio
	 * @param formatoMascara <code>String</code> com o formato da mascara
	 * @param caracterEspaco <code>String</code> com o caracter que aparecera como espac�o vazio
	 */
	public Mascara(String formatoMascara, char caracterEspaco) { //exemplo: ###-###
	       try{
	    	   this.setMask(formatoMascara); //Atribui a mascara
	    	   this.setPlaceholderCharacter(caracterEspaco); //Caracter para preenchimento
	       }
	       catch (Exception excecao) {
	    	   excecao.printStackTrace();
	       }
	}
	
	
	/** Cria uma mascara com o formato especificado, por exemplo ###-###
	 * @param formatoMascara <code>String</code> com o formato da mascara
	 * @return um objeto <code>MaskFormatter</code> com a mascara e com caracter de espaco vazio sendo espa�o 
	 */
	public static MaskFormatter mascara(String formatoMascara) { //exemplo: ###-###
        
	       MaskFormatter mascara = new MaskFormatter();
	       try{
	    	   mascara.setMask(formatoMascara); //Atribui a mascara
	    	   mascara.setPlaceholderCharacter(' '); //Caracter para preenchimento
	       }
	       catch (Exception excecao) {
	    	   excecao.printStackTrace();
	       }
	       return mascara;
	}
	
	/** Cria uma mascara com o formato especificado, por exemplo ###-###
	 * @param formatoMascara <code>String</code> com o formato da mascara
	 * @param caracterEspaco <code>char</code> com o caracter que aparecer� no espa�o vazio
	 * @return um objeto <code>MaskFormatter</code> com a mascara e com caracter de espaco vazio especificado
	 */
	public static MaskFormatter mascara(String formatoMascara, char caracterEspaco) { //exemplo: ###-###

	       MaskFormatter mascara = new MaskFormatter();
	       try{
	    	   mascara.setMask(formatoMascara); //Atribui a mascara
	    	   mascara.setPlaceholderCharacter(caracterEspaco); //Caracter para preenchimento
	       }
	       catch (Exception excecao) {
	    	   excecao.printStackTrace();
	       }
	       return mascara;
	}
	
	
	/** Muda o formato da mascara do objeto <code>Mascara</code> 
	 * @param formatoMascara <code>String</code> com o formato da mascara
	 */
	public void setMascara(String formatoMascara) {
		try{
			this.setMask(formatoMascara); //Atribui a mascara
		}
		catch (Exception excecao) {
			excecao.printStackTrace();
		}
	}
	
	/** Insere uma m�scara em um texto com o formato especificado, por exemplo ###-###
	 * @param texto <code>String</code> com o texto que ser� formatado
	 * @param formatoMascara <code>String</code> com o formato da mascara
	 * @return <code>String</code> com o texto <code>String</code> passado como par�metro formatado
	 */
    public static String formatarString(String texto, String formatoMascara) {  
        MaskFormatter mascara = null;
        String textoFormatado = null;
        
		try {
			mascara = new MaskFormatter(formatoMascara);
			mascara.setValueContainsLiteralCharacters(false);
			textoFormatado = mascara.valueToString(texto);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return textoFormatado;
    }  
	
} // class Mascara
