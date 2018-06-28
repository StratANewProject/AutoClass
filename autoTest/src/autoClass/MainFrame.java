package autoClass;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.SplashScreen;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import http.httpUtils;

import login.LoginDialog;

public class MainFrame extends JFrame {
	
	static HashSet<String> hash = new HashSet<String>();
	static String student_id=null;
	static String course_storage=null;
	static String postman_token=null;
	
	public static void main(String[] args) {
		init();
		
		SplashScreen splashScreen = SplashScreen.getSplashScreen();// ����������Ļ����
		JFrame login = new LoginDialog();// ��¼����
		if (splashScreen != null) {// ������Ļ����Ϊ��
			try {
				login.setDefaultCloseOperation(EXIT_ON_CLOSE);// ���õ�¼����Ĺرշ�ʽ
				Thread.sleep(3000);// �߳�����3��
			} catch (InterruptedException e) {
			}
		}
		
		login.setVisible(true);// ʹ��¼����ɼ�
	}
	
	public static void init() {
		httpUtils.uils.downloadCode();
	}
	
	public MainFrame(){
		super();// ���ø���JFrame�Ĺ�����
		initialize();
		
		
		
		
		JMenu jm=new JMenu("����") ;     //����JMenu�˵�����
		JMenuItem t1=new JMenuItem("�������") ;  //�˵���
		JMenuItem t2=new JMenuItem("ʹ��˵��") ;  //�˵���
		t1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	new about().CreateJFrame();
            }
        });
		t2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	new Read().CreateJFrame();
            }
        });
		jm.add(t1) ;   //���˵���Ŀ��ӵ��˵�
		jm.add(t2) ;
		
		JMenuBar  br=new  JMenuBar() ;  //�����˵�������
		
		br.add(jm) ;      //���˵����ӵ��˵�������
		
		this.setJMenuBar(br) ;  //Ϊ ��������  �˵�������
		
		Container c=getContentPane();
		c.setLayout(new GridLayout(2,1,10,10));
		
		JTextArea ta=new JTextArea(20,50);
		ta.setEditable(false);
		JScrollPane sp=new JScrollPane(ta);
		c.add(sp);
		
		JPanel p1=new JPanel(new GridLayout(2,1,10,10));
		JTextField jf=new JTextField();
		p1.add(jf);
		
		JTextArea blank=new JTextArea();
		blank.setBackground(getBackground());
		
		JTextArea notice=new JTextArea();
		notice.setBackground(getBackground());
		notice.setText("    ������γ̶�Ӧ�ı�ţ��ÿո�ָ����磺157 158\n    ����ύ����ȴ�һ��ʱ�䡣�γ̱�Ų鿴�������γ̽����������ַ��idֵ��\n    ��:http://acadol.hnu.edu.cn/LMS/study/course/onlineCourse/detail.do?id=158�Ŀγ̱����158��");
		notice.setFont(new java.awt.Font("΢���ź�",0,13));
		
		
		
		JPanel p2=new JPanel(new GridLayout(2,1,10,10));
		JPanel p4=new JPanel(new GridLayout(1,3,10,10));
		JButton jb=new JButton("�ύ");
		
		jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String in=jf.getText();
				System.out.println(in);
				String regEx = "^[0-9 ]*$";//������ʽ��֤���ǲ���ֻ�����ֺͿո�
				Pattern pattern = Pattern.compile(regEx);
				Matcher matcher = pattern.matcher(in);
				boolean rs = matcher.matches();
				if (rs) {
					String IDs[]=in.split(" ");
					for (String id:IDs) {
						httpUtils.uils.haveClass(Integer.parseInt(id));
					}
				} else {
					httpUtils.uils.error("����","�����ʽ����ȷ");
				}
            }
        });
		jb.setFont(new java.awt.Font("΢���ź�",0,20));
		
		
		JTextArea blank2=new JTextArea();
		blank2.setBackground(getBackground());
		
		p4.add(blank);
		p4.add(jb);
		p4.add(blank2);
		p2.add(notice);
		p2.add(p4);
		
		p1.add(p2);
		c.add(p1);
		
		reDirect(ta);
    }
	
	private void initialize() {// ��ʼ��������ķ���
		// �õ���ʾ����Ļ�Ŀ��
	    int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	    int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	    // ���崰��Ŀ��
	    int windowsWedth = 800;
	    int windowsHeight = 600;
		this.setTitle("������");//���ô������
		this.setBounds((width - windowsWedth) / 2,(height - windowsHeight) / 2, windowsWedth, windowsHeight);
	}
	
	public void reDirect(JTextArea jta){
		JTextAreaOutputStream out = new JTextAreaOutputStream (jta);
		System.setOut (new PrintStream (out));//��������ض��� 
		System.setErr(new PrintStream(out));//���������Ҳ�ض���,����e.pritnStackTrace
	}
	
	class JTextAreaOutputStream extends OutputStream
	{
	    private final JTextArea destination;
	 
	 
	    public JTextAreaOutputStream (JTextArea destination)
	    {
	        if (destination == null)
	            throw new IllegalArgumentException ("Destination is null");
	 
	 
	        this.destination = destination;
	    }
	    @Override
	    public void write(byte[] buffer, int offset, int length) throws IOException
	    {
	        final String text = new String (buffer, offset, length);
	        SwingUtilities.invokeLater(new Runnable ()
	            {
	                @Override
	                public void run() 
	                {
	                    destination.append (text);
	                }
	            });
	    }
	    @Override
	    public void write(int b) throws IOException
	    {
	        write (new byte [] {(byte)b}, 0, 1);
	    }
	}

	public static class about {
		public void CreateJFrame()
		{
			int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		    int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		    // ���崰��Ŀ��
		    int windowsWedth = 300;
		    int windowsHeight = 300;
			
		    JFrame jf = new JFrame("����");//���ڱ���
		    Container container = jf.getContentPane();//��������
		    JLabel jl = new JLabel("<html>2018/6/28<br>�����ߣ�SU<br>����szwpang@qq.com</html>");
		    //ʹ��ǩ�ϵ����־���
		    jl.setHorizontalAlignment(SwingConstants.CENTER);
		    jl.setFont(new java.awt.Font("΢���ź�",0,20));
		    container.add(jl);//�����������������Ӿ���ʾ����
		    container.setBackground(Color.WHITE);
		    jf.setVisible(true);//���ڿ���
		    jf.setBounds((width - windowsWedth) / 2,(height - windowsHeight) / 2, windowsWedth, windowsHeight);

		}
	}
	
	public static class Read {
		public void CreateJFrame()
		{
			int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		    int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		    // ���崰��Ŀ��
		    int windowsWedth = 300;
		    int windowsHeight = 300;
			
		    JFrame jf = new JFrame("ʹ��˵��");//���ڱ���
		    Container container = jf.getContentPane();//��������
		    JLabel jl = new JLabel("<html>�γ̱���µ�������Ƶ�����Ϊ�ѿ����״̬<br>ÿ����Ƶ�Ĺۿ�ʱ����Ϊһ��һ����Сʱ�ڵ������</html>");
		    //ʹ��ǩ�ϵ����־���
		    jl.setHorizontalAlignment(SwingConstants.CENTER);
		    jl.setFont(new java.awt.Font("΢���ź�",0,20));
		    container.add(jl);//�����������������Ӿ���ʾ����
		    container.setBackground(Color.WHITE);
		    jf.setVisible(true);//���ڿ���
		    jf.setBounds((width - windowsWedth) / 2,(height - windowsHeight) / 2, windowsWedth, windowsHeight);
		}
	}
}
