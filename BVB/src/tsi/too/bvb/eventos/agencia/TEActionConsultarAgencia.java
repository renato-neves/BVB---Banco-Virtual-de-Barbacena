package tsi.too.bvb.eventos.agencia;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import tsi.too.bvb.entidades.agencia.Agencia;
import tsi.too.bvb.gui.JanelaPopUpAviso;
import tsi.too.bvb.gui.JanelaPopUpErro;
import tsi.too.bvb.gui.agencia.IgConsultarAgencia;
import tsi.too.bvb.persistencia.AgenciaDAO;
import tsi.too.bvb.persistencia.BancoDeDadosBVB;
import tsi.too.bvb.validacoes.ValidarDados;

public class TEActionConsultarAgencia implements ActionListener {
	
	private IgConsultarAgencia igConsultarAgencia;

	public TEActionConsultarAgencia(IgConsultarAgencia igConsultarAgencia) {
		super();
		this.igConsultarAgencia = igConsultarAgencia;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == igConsultarAgencia.getBtnLimpar()) {
			igConsultarAgencia.limpaTabela();
			igConsultarAgencia.limpaCampos();
		}
		else if(e.getSource() == igConsultarAgencia.getBtnBuscar()) {
			String codigo = igConsultarAgencia.getCodigoTextField().getText();
			
			if(!igConsultarAgencia.getCodigoTextField().getText().isEmpty()) {
				if(ValidarDados.validarIntPositivo(codigo) && codigo.length() <= 4) {
					Agencia agencia = new AgenciaDAO().pesquisarCodigo(BancoDeDadosBVB.getInstance(), codigo);
					
					if(agencia != null) {
						if(!igConsultarAgencia.pesquisaTabela(agencia))
							igConsultarAgencia.addLinhasTabela(agencia);
						else
							new JanelaPopUpAviso(igConsultarAgencia, "BVB - Consulta de Ag�ncia", " A ag�ncia de c�digo '" +
									             codigo + "' j� foi consultada.");
					}
					else
						new JanelaPopUpAviso(igConsultarAgencia, "BVB - Consulta de Ag�ncia", " Nenhuma ag�ncia com o c�digo '" +
								             codigo + "' foi encontrada.");
				}
				else
					new JanelaPopUpErro(igConsultarAgencia, "BVB - Consulta de Ag�ncia", " O c�digo de ag�ncia '" +
							            codigo + "' � inv�lido!\n O campo de busca deve receber um valor inteiro e positivo"
							            		+ "\n com no m�ximo 4 d�gitos.");
			}
			else
				new JanelaPopUpErro(igConsultarAgencia, "BVB - Consulta de Ag�ncia", " Entrada inv�lida!\n" +
                                    " O campo de busca n�o pode ser vazio.");
		} // fim if(e.getSource() == igConsultarAgencia.getBtnBuscar())
	}

} // class TEActionConsultarAgencia
