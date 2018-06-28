package login;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;


import javax.imageio.ImageIO;
import javax.swing.*;

import autoClass.MainFrame;
import http.httpUtils;

public class LoginDialog extends JFrame {// ��¼����
	private static final long serialVersionUID = 1L;
	private LoginPanel loginPanel = null;// ��¼���
	private JLabel jLabel = null;// ���û�������ǩ
	private JTextField userField = null;// ���û������ı���
	private JLabel jLabel1 = null;// �����롱��ǩ
	private JPasswordField passwordField = null;// �����롱�ı���
	private JButton loginButton = null;// ����¼����ť
	private JButton exitButton = null;// ���˳�����ť
	private JButton codeButton = null;// ����֤�롱��ť
	private static String userStr;// ���û������ı����е�����
	private JTextField codeField = null;// ����֤�롱�ı���
	private static String codeStr;// ����֤�롱�ı����е�����
	private MainFrame mainFrame;// ������

	public LoginDialog() {// ��¼����Ĺ��췽��
		try {
			// ���õ�¼����ķ��
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			mainFrame = new MainFrame();// ʵ����������
			initialize();// ��ʼ����¼����
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private LoginPanel getLoginPanel() {// ��ʼ��loginPanel��¼���ķ���
		if (loginPanel == null) {// ��¼�����û�����ʱ
			jLabel1 = new JLabel();// �����롱��ǩ
			jLabel1.setBounds(new Rectangle(86, 51, 55, 18));// ���á����롱��ǩ��λ������
			jLabel1.setText("�ܡ��룺");// ���á����롱��ǩ���ı�����
			jLabel = new JLabel();// ���û�������ǩ
			jLabel.setText("�û�����");// ���á��û�������ǩ���ı�����
			jLabel.setBounds(new Rectangle(86, 21, 56, 18));// ���á��û�������ǩ��λ������
			loginPanel = new LoginPanel();// ��¼���
			loginPanel.setLayout(null);// ���õ�¼���Ĳ���Ϊ���Բ���
			loginPanel.setBackground(new Color(0xD8DDC7));// ���õ�¼���ı���ɫ
			loginPanel.add(jLabel, null);// �ѡ��û�������ǩ���ڵ�¼�����
			loginPanel.add(getUserField(), null);// �ѡ��û������ı������ڵ�¼�����
			loginPanel.add(getCodeField(), null);// �ѡ���֤�롱�ı������ڵ�¼�����
			loginPanel.add(jLabel1, null);// �ѡ����롱��ǩ���ڵ�¼�����
			loginPanel.add(getPasswordField(), null);// �ѡ����롱�ı������ڵ�¼�����
			loginPanel.add(getLoginButton(), null);// �ѡ���¼����ť���ڵ�¼�����
			loginPanel.add(getExitButton(), null);// �ѡ��˳�����ť���ڵ�¼�����
			try {
				loginPanel.add(getCodeButton(), null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// �ѡ��˳�����ť���ڵ�¼�����
		}
		return loginPanel;// ���ص�¼���
	}

	private JTextField getCodeField() {// ��ʼ������֤�롱�ı���
		if (codeField == null) {// ����֤�롱�ı������Ϊ��ʱ
			codeField = new JTextField();// ʵ��������֤�롱�ı���
			codeField.setBounds(new Rectangle(142, 79, 127, 22));// ���á��û������ı����λ�úͿ��
		}
		return codeField;// ���ء���֤�롱�ı���
	}
	
	private JTextField getUserField() {// ��ʼ�����û������ı���
		if (userField == null) {// ���û������ı������Ϊ��ʱ
			userField = new JTextField();// ʵ�������û������ı���
			userField.setBounds(new Rectangle(142, 19, 127, 22));// ���á��û������ı����λ�úͿ��
		}
		return userField;// ���ء��û������ı���
	}

	private JPasswordField getPasswordField() {// ��ʼ�������롱�ı���
		if (passwordField == null) {// �����롱�ı������Ϊ��ʱ
			passwordField = new JPasswordField();// ʵ���������롱�ı���
			passwordField.setBounds(new Rectangle(142, 49, 127, 22));// ���á����롱�ı����λ�úͿ��
			passwordField.addKeyListener(new KeyAdapter() {// Ϊ�����롱�ı�����Ӽ���ʱ��ļ���
				public void keyTyped(KeyEvent e) {
					if (e.getKeyChar() == '\n')// ���µİ����ǻس�ʱ
						loginButton.doClick();// ����¼����ťִ�е���¼�
				}
			});
		}
		return passwordField;// ���ء����롱�ı���
	}

	private JButton getLoginButton() {// ��ʼ������¼����ť
		if (loginButton == null) {// ����¼����ť����Ϊ��ʱ
			loginButton = new JButton();// ʵ��������¼����ť
			loginButton.setBounds(new Rectangle(142, 114, 48, 20));// ���á���¼����ť��λ�úͿ��
			loginButton.setIcon(new ImageIcon(getClass().getResource("/res/loginButton.jpg")));// ���á���¼����ť��ͼ��
			loginButton.addActionListener(new ActionListener() {// Ϊ����¼����ť��Ӷ����¼��ļ���
				public void actionPerformed(ActionEvent e) {
					try {
						userStr = userField.getText();// ��á��û������ı����е�����
						String passStr = new String(passwordField.getPassword());// ��á����롱�ı����е�����
						String codeStr = codeField.getText();
						System.out.println("�û���:\t"+userStr);
						System.out.println("����:\t"+passStr);
						System.out.println("��֤��:\t"+codeStr);
						//httpUtils.uils.downloadCode();
						httpUtils.uils.login(userStr,passStr,codeStr,mainFrame);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					//mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);// ����������Ĺرշ�ʽ
					//mainFrame.setVisible(true);// ʹ������ɼ�
					//MainFrame.getCzyStateLabel().setText(userStr);// �����������в���Ա��ǩ��������
					setVisible(false);// ʹ��¼���岻�ɼ�
				}
			});
		}
		return loginButton;// ���ء���¼����ť
	}

	private JButton getExitButton() {// ��ʼ�����˳�����ť
		if (exitButton == null) {// ���˳�����ť����Ϊ��ʱ
			exitButton = new JButton();// ʵ�������˳�����ť
			exitButton.setBounds(new Rectangle(221, 114, 48, 20));// ���á��˳�����ť��λ�úͿ��
			exitButton.setIcon(new ImageIcon(getClass().getResource("/res/exitButton.jpg")));// ���á��˳�����ť��ͼ��
			exitButton.addActionListener(new ActionListener() {// Ϊ���˳�����ť��򰮶����¼��ļ���
				public void actionPerformed(ActionEvent e) {
					System.exit(0);// �˳���ǰ��Ӧ�ó���
				}
			});
		}
		return exitButton;// ���ء��˳�����ť
	}

	private void initialize() {// ��ʼ����¼����
		Dimension size = getToolkit().getScreenSize();// �����Ļ�ߴ�
		setLocation((size.width - 296) / 2, (size.height - 188) / 2);// ���õ�¼����
		setSize(296, 188);// ���õ�¼����Ŀ��
		this.setTitle("�Զ�ˢ��ϵͳ");// ���õ�¼����ı���
		setContentPane(getLoginPanel());// ����¼������ڵ�¼������
	}

	public String getUserStr() {// ��á��û������ı����е�����
		return userStr;// ���ء��û������ı����е�����
	}
	
	public String getCodeStr() {// ��á���֤�롱�ı����е�����
		return codeStr;// ���ء���֤�롱�ı����е�����
	}

	private JButton getCodeButton() throws IOException {// ��ʼ������֤�롱��ť
		if (codeButton == null) {// ����֤�롱��ť����Ϊ��ʱ
			codeButton = new JButton();// ʵ��������֤�롱��ť
			codeButton.setBounds(new Rectangle(72, 80, 60, 20));// ���á���֤�롱��ť��λ�úͿ��
			codeButton.setIcon(new ImageIcon(ImageIO.read(new File("/res/code.jpg"))));// ���á���֤�롱��ť��ͼ��"./src/res/code.jpg"
			codeButton.addActionListener(new ActionListener() {// Ϊ����֤�롱��ť�����¼��ļ���
				public void actionPerformed(ActionEvent e) {
					httpUtils.uils.downloadCode();
					try {
						codeButton.setIcon(new ImageIcon(ImageIO.read(new File("/res/code.jpg"))));//"./src/res/code.jpg"
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return codeButton;// ���ء���֤�롱��ť
	}
}
