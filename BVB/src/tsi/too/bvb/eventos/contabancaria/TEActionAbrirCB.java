package tsi.too.bvb.eventos.contabancaria;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import tsi.too.bvb.entidades.contabancaria.ContaBancaria;
import tsi.too.bvb.entidades.tiposenumerados.TipoConta;
import tsi.too.bvb.gui.JanelaPopUpInfo;
import tsi.too.bvb.gui.PainelConfCad;
import tsi.too.bvb.gui.TratadorDeCampos;
import tsi.too.bvb.gui.contabancaria.IgAbrirContaBancaria;
import tsi.too.bvb.gui.contabancaria.PainelAbContaDadosChave;
import tsi.too.bvb.gui.contabancaria.PainelAbContaDadosSec;
import tsi.too.bvb.gui.contabancaria.PainelAbContaSenhas;
import tsi.too.bvb.persistencia.BancoDeDadosBVB;
import tsi.too.bvb.persistencia.ContaBancariaDAO;

/** Classe para tratar os eventos de a��o da janela <code>IgAbrirContaBancaria</code> e de seu painel <code>PainelAbContaDadosChave</code>
 * 
 * @author Gian Carlos Barros Hon�rio
 * @author Diego Oliveira
 * 
 * @see ActionListener
 */
public class TEActionAbrirCB implements ActionListener {
	
	private IgAbrirContaBancaria igAbrirContaBancaria;
	private PainelAbContaDadosChave painelAbContaDadosChave;
	private ContaBancaria contaBancaria;
	
	/** Cria uma inst�ncia do Tratador de eventos de a��o do painel <code>painelCadCliente</code>
	 * @param painelAbContaDadosChave <code>PainelAbContaDadosChave</code> que ser� manipulado
	 */
	public TEActionAbrirCB(PainelAbContaDadosChave painelAbContaDadosChave) {
		super();
		this.painelAbContaDadosChave = painelAbContaDadosChave;
	}

	/** Cria uma inst�ncia do Tratador de eventos de a��o da janela <code>IgAbrirContaBancaria</code>
	 * @param igAbrirContaBancaria <code>IgAbrirContaBancaria</code> que ser� manipulada
	 * @param contaBancaria <code>ContaBancaria</code> refer�nte ao objeto que ser� manipulado
	 * 
	 * @see ContaBancaria
	 */
	public TEActionAbrirCB(IgAbrirContaBancaria igAbrirContaBancaria, ContaBancaria contaBancaria) {
		super();
		this.painelAbContaDadosChave = null;
		this.igAbrirContaBancaria = igAbrirContaBancaria;
		this.contaBancaria = contaBancaria;
	}
	
	/** Trata os eventos de a��o dos elementos da janela <code>IgAbrirContaBancaria</code> e de seu painel <code>PainelAbContaDadosChave</code>
	 * @see ActionListener
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(painelAbContaDadosChave != null && e.getSource() == painelAbContaDadosChave.getBtnValidarCodAgencia())
			painelAbContaDadosChave.validarCampoCodAgencia();
		else if(painelAbContaDadosChave != null && e.getSource() == painelAbContaDadosChave.getBtnValidarCpf())
			painelAbContaDadosChave.validarCampoCpf();
		else {
			TratadorDeCampos painelVisivel = igAbrirContaBancaria.obterPainelVisivel();
			
			if(e.getSource() == igAbrirContaBancaria.getBtnLimpar()) {
				painelVisivel.limparCampos();
				igAbrirContaBancaria.setLblCamposErrados(false);
			}
			else if(e.getSource() == igAbrirContaBancaria.getBtnProximo()) {
				if(painelVisivel.validarCampos()) {
					igAbrirContaBancaria.setLblCamposErrados(false);
					painelVisivel.salvarCampos(contaBancaria);
					
					if(painelVisivel instanceof PainelAbContaDadosChave) {
						igAbrirContaBancaria.getpAbContaDadosSec().setTipoConta(TipoConta.obterTipoConta(igAbrirContaBancaria
								                                                .getpAbContaDadosChave().obterRadioBtnSelecionado()));		
                		// Exibe ou esconde as op��es da conta sal�rio de acordo com o tipo da conta escolhido.
						igAbrirContaBancaria.getpAbContaDadosSec().visualizacaoOpcContaSal();
						// Atualiza o 'Tool Tip Text' do campo Saldo do painel de dados secund�rios.
						igAbrirContaBancaria.getpAbContaDadosSec().atualizaToolTipTextSaldo();

						igAbrirContaBancaria.getCardLayout().show(igAbrirContaBancaria.getCardPanel(), "dadosSecundarioPanel");
						igAbrirContaBancaria.setTxtpnSubTitulo("Insira os dados secund�rios da nova conta banc�ria.");
						igAbrirContaBancaria.getpAbContaDadosSec().inserirBordasPadrao();
						igAbrirContaBancaria.setProgressBar(25);
						igAbrirContaBancaria.getpAbContaDadosSec().setVisible(true);
						igAbrirContaBancaria.getBtnAnterior().setVisible(true);
					}
					else if(painelVisivel instanceof PainelAbContaDadosSec) {
						igAbrirContaBancaria.getCardLayout().show(igAbrirContaBancaria.getCardPanel(), "senhasPanel");
						igAbrirContaBancaria.setTxtpnSubTitulo("Insera as senhas da nova conta banc�ria.");
						igAbrirContaBancaria.getpAbContaSenhas().inserirBordasPadrao();
						igAbrirContaBancaria.setProgressBar(50);
						igAbrirContaBancaria.getpAbContaSenhas().setVisible(true);
					}
					else if(painelVisivel instanceof PainelAbContaSenhas) {
						igAbrirContaBancaria.getCardLayout().show(igAbrirContaBancaria.getCardPanel(), "confCadPanel");
						igAbrirContaBancaria.setTxtpnSubTitulo("Confirme os dados da nova conta banc�ria.");
						igAbrirContaBancaria.setProgressBar(75);
						igAbrirContaBancaria.getpConfCad().setDadosEditorPane(contaBancaria.exibeDadosFormatados());
						igAbrirContaBancaria.getpConfCad().setVisible(true);
						igAbrirContaBancaria.getBtnLimpar().setVisible(false);
						igAbrirContaBancaria.getBtnProximo().setVisible(false);
						igAbrirContaBancaria.getBtnFinalizar().setVisible(true);
					}
				}
				else
					igAbrirContaBancaria.setLblCamposErrados(true);
			} // fim if(e.getSource() == igAbrirContaBancaria.getBtnProximo())
			
			else if(e.getSource() == igAbrirContaBancaria.getBtnAnterior()) {
				if(painelVisivel instanceof PainelAbContaDadosSec) {
					igAbrirContaBancaria.getCardLayout().show(igAbrirContaBancaria.getCardPanel(), "dadosChavePanel");
					igAbrirContaBancaria.setTxtpnSubTitulo("Insira os dados chave da nova conta banc�ria.");
					igAbrirContaBancaria.setProgressBar(0);
					igAbrirContaBancaria.setLblCamposErrados(false);
					igAbrirContaBancaria.getpAbContaDadosChave().setVisible(true);
					igAbrirContaBancaria.getBtnAnterior().setVisible(false);
				}
				else if(painelVisivel instanceof PainelAbContaSenhas) {
					igAbrirContaBancaria.getCardLayout().show(igAbrirContaBancaria.getCardPanel(), "dadosSecundarioPanel");
					igAbrirContaBancaria.setTxtpnSubTitulo("Insira os dados secund�rios da nova conta banc�ria.");
					igAbrirContaBancaria.setProgressBar(25);
					igAbrirContaBancaria.setLblCamposErrados(false);
					igAbrirContaBancaria.getpAbContaDadosSec().setVisible(true);
				}
				else if(painelVisivel instanceof PainelConfCad) {
					igAbrirContaBancaria.getCardLayout().show(igAbrirContaBancaria.getCardPanel(), "senhasPanel");
					igAbrirContaBancaria.setTxtpnSubTitulo("Insera as senhas da nova conta banc�ria.");
					igAbrirContaBancaria.setProgressBar(50);
					igAbrirContaBancaria.setLblCamposErrados(false);
					igAbrirContaBancaria.getpAbContaSenhas().setVisible(true);
					igAbrirContaBancaria.getBtnLimpar().setVisible(true);
					igAbrirContaBancaria.getBtnProximo().setVisible(true);
					igAbrirContaBancaria.getBtnFinalizar().setVisible(false);
				}
			} // fim if(e.getSource() == igAbrirContaBancaria.getBtnAnterior())
			
			else if(e.getSource() == igAbrirContaBancaria.getBtnFinalizar()) {
				ContaBancariaDAO contaBancariaDAO = new ContaBancariaDAO();
				contaBancaria.setNumConta(contaBancariaDAO.proximoValorSequencia(BancoDeDadosBVB.getInstance()));
				contaBancariaDAO.criar(BancoDeDadosBVB.getInstance(), contaBancaria);
				igAbrirContaBancaria.setProgressBar(100);
				
				igAbrirContaBancaria.getpConfCad().setDadosEditorPane(contaBancaria.toString());
				igAbrirContaBancaria.getpAbContaDadosChave().setNumContaTextField(Integer.toString(contaBancaria.getNumConta()));
				new JanelaPopUpInfo(igAbrirContaBancaria, "BVB - Abertura de Conta Banc�ria", " Aberuta de Conta Banc�ria Realizada com Sucesso!",
				                    contaBancaria.toString());
				igAbrirContaBancaria.dispose();
			} // fim if(e.getSource() == igAbrirContaBancaria.getBtnFinalizar())
		} // fim else
	}

} // class TEActionAbrirCB
