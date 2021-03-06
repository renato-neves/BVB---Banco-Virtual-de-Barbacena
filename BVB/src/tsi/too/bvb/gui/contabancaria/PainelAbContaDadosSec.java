package tsi.too.bvb.gui.contabancaria;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import tsi.too.bvb.entidades.JNumberFormatField;
import tsi.too.bvb.entidades.contabancaria.ContaBancaria;
import tsi.too.bvb.entidades.tiposenumerados.TipoConta;
import tsi.too.bvb.gui.TratadorDeCampos;
import tsi.too.bvb.validacoes.ValidarDados;

/** Classe que define o painel dados secund�rios utilizado pelas janelas <code>IgAbrirContaBancaria</code> e 
 * <code>IgCriarAplicacao</code>
 * 
 * @author Gian Carlos Barros Hon�rio
 * @author Diego Oliveira
 * 
 * @see JPanel
 * @see IgAbrirContaBancaria
 * @see IgCriarAplicacao
 * @see TratadorDeCampos
 */
public class PainelAbContaDadosSec extends JPanel implements TratadorDeCampos {

	/**
	 * @serial
	 */
	private static final long serialVersionUID = -690399083609439414L;
	
	private TipoConta tipoConta;
	private JCheckBox chckbxContaSalario;
	private JLabel lblSaldo;
	private JTextField saldoTextField;
	private UtilDateModel model;
	private JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;
	private JLabel lblContaSalario;

	/** Cria uma inst�ncia do painel dados secund�rios utilizado pelas janelas <code>IgAbrirContaBancaria</code> e 
	 * <code>IgCriarAplicacao</code>
	 */	
	public PainelAbContaDadosSec() {
		setLayout(null);
		
		JLabel lblCodAgencia = new JLabel("Data de Abertura:");
		lblCodAgencia.setDisplayedMnemonic(KeyEvent.VK_D);
		lblCodAgencia.setBounds(10, 18, 115, 14);
		add(lblCodAgencia);

		model = new UtilDateModel();
		datePanel = new JDatePanelImpl(model);
		datePicker = new JDatePickerImpl(datePanel);
		datePicker.getJFormattedTextField().setToolTipText("este campo \u00E9 de preenchimento obrigat\u00F3rio, a data n\u00E3o pode ser maior que a data atual e o ano n\u00E3o pode ser menor que 2001 ");
		lblCodAgencia.setLabelFor(datePicker.getJFormattedTextField());
		datePicker.setBounds(130, 11, 112, 28);
		datePanel.setBounds(100, 15, 112, 24);
		add(datePicker);
		
		lblSaldo = new JLabel("Saldo:");
		lblSaldo.setDisplayedMnemonic(KeyEvent.VK_S);
		lblSaldo.setBounds(10, 55, 60, 14);
		add(lblSaldo);
		
		chckbxContaSalario = new JCheckBox("Conta Sal\u00E1rio");
		chckbxContaSalario.setEnabled(false);
		chckbxContaSalario.setToolTipText("marque se a conta for utilizada para receber sal\u00E1rio");
		chckbxContaSalario.setMnemonic(KeyEvent.VK_O);
		chckbxContaSalario.setBounds(100, 109, 112, 24);
		add(chckbxContaSalario);
		
		lblContaSalario = new JLabel("Deseja utilizar esta conta para receber seu sal\u00E1rio?");
		lblContaSalario.setEnabled(false);
		lblContaSalario.setLabelFor(chckbxContaSalario);
		lblContaSalario.setDisplayedMnemonic(KeyEvent.VK_E);
		lblContaSalario.setBounds(10, 85, 378, 16);
		add(lblContaSalario);
		
		saldoTextField = new JNumberFormatField(new DecimalFormat("R$0.00"));
		((JNumberFormatField) saldoTextField).setLimit(17);
		saldoTextField.setToolTipText("este campo \u00E9 de preenchimento obrigat\u00F3rio e deve conter apenas d\u00EDgitos decimais");
		lblSaldo.setLabelFor(saldoTextField);
		saldoTextField.setBounds(130, 52, 269, 20);
		saldoTextField.setColumns(10);
		add(saldoTextField);
	}

	/** Limpa os campos do painel
	 */
	@Override
	public void limparCampos() {
		inserirBordasPadrao();
		
		datePicker.getModel().setValue(null);
		saldoTextField.setText("");
		
		chckbxContaSalario.setSelected(false);
	}

	/** Salva os dados inseridos na janela em um objeto do tipo <code>ContaBancaria</code>
	 * @param contaBancaria <code>Object</code> refer�nte ao objeto que ser� salvo
	 */
	@Override
	public void salvarCampos(Object contaBancaria) {
		((ContaBancaria) contaBancaria).setDataAbertura((Date) datePicker.getModel().getValue());
		((ContaBancaria) contaBancaria).setSaldo(Double.parseDouble(saldoTextField.getText().replace(",", ".").replace("R", "").replace("$", "")));
		((ContaBancaria) contaBancaria).setContaSalario(chckbxContaSalario.isSelected());
	}

	/** Verifica se os campos do painel foram preenchidos corretamente. Os campos errados 
	 * recebem uma borda vermelha
	 *  @return <code>boolean</code> com <code>true</code> caso todos os campos tenham sido preenchidos corretamente, 
	 *  e <code>false</code> caso contr�rio
	 */
	@Override
	public boolean validarCampos() {
		boolean valido = true;
		
		if(ValidarDados.validarData((Date) datePicker.getModel().getValue()))
			datePicker.setBorder(BorderFactory.createLineBorder(getBackground (), 2));
		else {
			datePicker.setBorder(new LineBorder(Color.RED));
			valido = false;
		}
		
		if(!ValidarDados.validarDoublePositivo(saldoTextField.getText().replace(",", ".").replace("R", "").replace("$", ""))) {
			saldoTextField.setBorder(new LineBorder(Color.RED));
			valido = false;
		}
		else {
			// Conta Corrente
			if(tipoConta.getNumero() == TipoConta.CONTA_CORRENTE.getNumero()) {
				if(!chckbxContaSalario.isSelected() && Double.parseDouble(saldoTextField.getText()
				   .replace(",", ".").replace("R", "").replace("$", "")) < 300) {
					saldoTextField.setBorder(new LineBorder(Color.RED));
					valido = false;
				}
				else
					saldoTextField.setBorder(UIManager.getBorder("TextField.border"));
			}
			
			// Conta Poupan�a
			else if(tipoConta.getNumero() == TipoConta.CONTA_POUPANCA.getNumero()) {
				if(Double.parseDouble(saldoTextField.getText().replace(",", ".").replace("R", "").replace("$", "")) < 50) {
					saldoTextField.setBorder(new LineBorder(Color.RED));
					valido = false;
				}
				else
					saldoTextField.setBorder(UIManager.getBorder("TextField.border"));
			}
			
			// Conta FIF Pr�tico
			else if(tipoConta.getNumero() == TipoConta.FIF_PRATICO.getNumero()) {
				if(Double.parseDouble(saldoTextField.getText().replace(",", ".").replace("R", "").replace("$", "")) < 2000) {
					saldoTextField.setBorder(new LineBorder(Color.RED));
					valido = false;
				}
				else
					saldoTextField.setBorder(UIManager.getBorder("TextField.border"));
			}
			
			// Conta FIF Executivo
			else if(tipoConta.getNumero() == TipoConta.FIF_EXECUTIVO.getNumero()) {
				if(Double.parseDouble(saldoTextField.getText().replace(",", ".").replace("R", "").replace("$", "")) < 15000) {
					saldoTextField.setBorder(new LineBorder(Color.RED));
					valido = false;
				}
				else
					saldoTextField.setBorder(UIManager.getBorder("TextField.border"));
			}
		}
		
		return valido;
	}
	
	/** Insere a borda padr�o nos campos do painel
	 */
	@Override
	public void inserirBordasPadrao() {
		datePicker.setBorder(BorderFactory.createLineBorder(getBackground (), 2));
		saldoTextField.setBorder(UIManager.getBorder("TextField.border"));
	}

	/** Verifica se a caixa de checagem conta sal�rio deve ser exibida. Ela deve ser exibida caso o tipo da 
	 * conta seja corrente
	 */
	public void visualizacaoOpcContaSal() {
		boolean mostrar = true;
		
		if(tipoConta.getNumero() != TipoConta.CONTA_CORRENTE.getNumero()) {
			mostrar = false;
			chckbxContaSalario.setSelected(mostrar);
		}
		
		lblContaSalario.setEnabled(mostrar);
		chckbxContaSalario.setEnabled(mostrar);
	}
	
	/** Atualiza a dica do campo saldo de acordo com o tipo da conta
	 */
	public void atualizaToolTipTextSaldo() {
		final String SALDO_TOOLTIPTEXT = "este campo \u00E9 de preenchimento obrigat\u00F3rio, deve conter apenas d\u00EDgitos decimais";
		
		if(tipoConta.getNumero() == TipoConta.CONTA_CORRENTE.getNumero())
			saldoTextField.setToolTipText(SALDO_TOOLTIPTEXT + " e valor de no m�nimo R$ 300,00 se esta conta n�o for utilizada como conta sal�rio");
		else if(tipoConta.getNumero() == TipoConta.CONTA_POUPANCA.getNumero())
			saldoTextField.setToolTipText(SALDO_TOOLTIPTEXT + " e valor de no m�nimo R$ 50,00");
		else if(tipoConta.getNumero() == TipoConta.FIF_PRATICO.getNumero())
			saldoTextField.setToolTipText(SALDO_TOOLTIPTEXT + " e valor de no m�nimo R$ 2.000,00");
		else if(tipoConta.getNumero() == TipoConta.FIF_EXECUTIVO.getNumero())
			saldoTextField.setToolTipText(SALDO_TOOLTIPTEXT + " e valor de no m�nimo R$ 15.000,00");
	}

	/** Retorna o tipo da conta
	 * @return <code>TipoConta</code> com o tipo da conta
	 */
	public TipoConta getTipoConta() {
		return tipoConta;
	}

	/** Muda o campo de tipo da conta
	 * @param tipoConta <code>TipoConta</code> com o novo tipo da conta
	 * 
	 * @see TipoConta
	 */
	public void setTipoConta(TipoConta tipoConta) {
		this.tipoConta = tipoConta;
	}
	
} // class PainelAbContaDadosSec
