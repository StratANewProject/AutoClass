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
		
		SplashScreen splashScreen = SplashScreen.getSplashScreen();// 创建闪现屏幕对象
		JFrame login = new LoginDialog();// 登录窗体
		if (splashScreen != null) {// 闪现屏幕对象不为空
			try {
				login.setDefaultCloseOperation(EXIT_ON_CLOSE);// 设置登录窗体的关闭方式
				Thread.sleep(3000);// 线程休眠3秒
			} catch (InterruptedException e) {
			}
		}
		
		login.setVisible(true);// 使登录窗体可见
	}
	
	public static void init() {
		httpUtils.uils.downloadCode();
	}
	
	public MainFrame(){
		super();// 调用父类JFrame的构造器
		initialize();
		
		
		
		
		JMenu jm=new JMenu("关于") ;     //创建JMenu菜单对象
		JMenuItem t1=new JMenuItem("关于软件") ;  //菜单项
		JMenuItem t2=new JMenuItem("使用说明") ;  //菜单项
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
		jm.add(t1) ;   //将菜单项目添加到菜单
		jm.add(t2) ;
		
		JMenuBar  br=new  JMenuBar() ;  //创建菜单工具栏
		
		br.add(jm) ;      //将菜单增加到菜单工具栏
		
		this.setJMenuBar(br) ;  //为 窗体设置  菜单工具栏
		
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
		notice.setText("    请输入课程对应的编号，用空格分隔。如：157 158\n    点击提交后请等待一段时间。课程编号查看方法：课程界面浏览器地址的id值。\n    如:http://acadol.hnu.edu.cn/LMS/study/course/onlineCourse/detail.do?id=158的课程编号是158。");
		notice.setFont(new java.awt.Font("微软雅黑",0,13));
		
		
		
		JPanel p2=new JPanel(new GridLayout(2,1,10,10));
		JPanel p4=new JPanel(new GridLayout(1,3,10,10));
		JButton jb=new JButton("提交");
		
		jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String in=jf.getText();
				System.out.println(in);
				String regEx = "^[0-9 ]*$";//正则表达式验证看是不是只有数字和空格
				Pattern pattern = Pattern.compile(regEx);
				Matcher matcher = pattern.matcher(in);
				boolean rs = matcher.matches();
				if (rs) {
					String IDs[]=in.split(" ");
					for (String id:IDs) {
						httpUtils.uils.haveClass(Integer.parseInt(id));
					}
				} else {
					httpUtils.uils.error("错误","输入格式不正确");
				}
            }
        });
		jb.setFont(new java.awt.Font("微软雅黑",0,20));
		
		
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
	
	private void initialize() {// 初始化主窗体的方法
		// 得到显示器屏幕的宽高
	    int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	    int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	    // 定义窗体的宽高
	    int windowsWedth = 800;
	    int windowsHeight = 600;
		this.setTitle("主界面");//设置窗体标题
		this.setBounds((width - windowsWedth) / 2,(height - windowsHeight) / 2, windowsWedth, windowsHeight);
	}
	
	public void reDirect(JTextArea jta){
		JTextAreaOutputStream out = new JTextAreaOutputStream (jta);
		System.setOut (new PrintStream (out));//设置输出重定向 
		System.setErr(new PrintStream(out));//将错误输出也重定向,用于e.pritnStackTrace
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
		    // 定义窗体的宽高
		    int windowsWedth = 300;
		    int windowsHeight = 300;
			
		    JFrame jf = new JFrame("关于");//窗口标题
		    Container container = jf.getContentPane();//创建容器
		    JLabel jl = new JLabel("<html>2018/6/28<br>开发者：SU<br>邮箱szwpang@qq.com</html>");
		    //使标签上的文字居中
		    jl.setHorizontalAlignment(SwingConstants.CENTER);
		    jl.setFont(new java.awt.Font("微软雅黑",0,20));
		    container.add(jl);//添加上面的组件，不添加就显示不到
		    container.setBackground(Color.WHITE);
		    jf.setVisible(true);//窗口可视
		    jf.setBounds((width - windowsWedth) / 2,(height - windowsHeight) / 2, windowsWedth, windowsHeight);

		}
	}
	
	public static class Read {
		public void CreateJFrame()
		{
			int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		    int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		    // 定义窗体的宽高
		    int windowsWedth = 300;
		    int windowsHeight = 300;
			
		    JFrame jf = new JFrame("使用说明");//窗口标题
		    Container container = jf.getContentPane();//创建容器
		    JLabel jl = new JLabel("<html>课程编号下的所有视频都会变为已看完的状态<br>每个视频的观看时长设为一个一到两小时内的随机数</html>");
		    //使标签上的文字居中
		    jl.setHorizontalAlignment(SwingConstants.CENTER);
		    jl.setFont(new java.awt.Font("微软雅黑",0,20));
		    container.add(jl);//添加上面的组件，不添加就显示不到
		    container.setBackground(Color.WHITE);
		    jf.setVisible(true);//窗口可视
		    jf.setBounds((width - windowsWedth) / 2,(height - windowsHeight) / 2, windowsWedth, windowsHeight);
		}
	}
}
