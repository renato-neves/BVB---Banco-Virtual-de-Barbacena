package tsi.too.bvb.eventos.funcionario;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import tsi.too.bvb.entidades.funcionario.Funcionario;
import tsi.too.bvb.gui.JanelaPopUpInfo;
import tsi.too.bvb.gui.funcionario.IgCadFuncionario;
import tsi.too.bvb.persistencia.BancoDeDadosBVB;
import tsi.too.bvb.persistencia.FuncionarioDAO;
import tsi.too.bvb.validacoes.ValidarDados;

public class TEMouseCadastrarFuncionario extends MouseAdapter {
	
	private IgCadFuncionario igCadFuncionario;
	private Funcionario funcionario;

	public TEMouseCadastrarFuncionario(IgCadFuncionario igCadFuncionario, Funcionario funcionario) {
		super();
		this.igCadFuncionario = igCadFuncionario;
		this.funcionario = funcionario;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseClicked(e);
		
		if(e.getButton() == MouseEvent.BUTTON1) { // bot�o esquerdo do mouse
			if(e.getSource() == igCadFuncionario.getBtnLimpar()) {
				igCadFuncionario.setLoginTextField("");
				igCadFuncionario.setPasswordField("");
			}
			
			if(e.getSource() == igCadFuncionario.getBtnFinalizar()) {
				boolean validacao = true;
				
				if(igCadFuncionario.validarLogin() == false)
					validacao = false;
				
				if(ValidarDados.validarSenhaFunc(igCadFuncionario.getPasswordField().getPassword()) == false) {
					igCadFuncionario.getPasswordField().setBorder(new LineBorder(Color.RED));
					validacao = false;
				}
				else
					igCadFuncionario.getPasswordField().setBorder(UIManager.getBorder("PasswordField.border"));
				
				if(validacao == true) {
					igCadFuncionario.setLblCamposErrados(false);
					igCadFuncionario.salvarCampos(funcionario);
					igCadFuncionario.getLoginTextField().setBorder(UIManager.getBorder("TextField.border"));
					igCadFuncionario.getPasswordField().setBorder(UIManager.getBorder("PasswordField.border"));
					
					FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
					funcionarioDAO.criar(BancoDeDadosBVB.getInstance(), funcionario);
					
					new JanelaPopUpInfo(igCadFuncionario, "Novo Cadastro de Funcion�rio", "Cadastro do Funcion�rio Realizado com Sucesso!",
							            funcionario.toString());
					igCadFuncionario.dispose();
				}
				else
					igCadFuncionario.setLblCamposErrados(true);
			}
			
			if(e.getSource() == igCadFuncionario.getBtnVerificar())
				igCadFuncionario.validarLogin();
		}
	}

} // class TEMouseCadastrarFuncionario
