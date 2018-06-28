package http;

import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import autoClass.MainFrame;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static javax.swing.JFrame.*;//引入JFramed的静态常量
public  class httpUtils {
	public static class uils {
		
		static String courseInfoStr=null;
		static String urlinit="http://116.62.11.214:8081/coursePlayer/init.do";
		static String ip="http://116.62.11.214:8081";
		static HashSet<String> hash=new HashSet<String>();
		static String student_id=null;
		public final static int CONNECT_TIMEOUT =30;
	    public final static int READ_TIMEOUT=30;
	    public final static int WRITE_TIMEOUT=30;
		
		public static OkHttpClient client = new OkHttpClient.Builder()
				.cookieJar(new CookieJar() {
					private final List<Cookie> cookieStore = new ArrayList<Cookie>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.addAll(cookies);
                    }
                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        return cookieStore != null ? cookieStore : new ArrayList<Cookie>();
                    }
                })
				.readTimeout(READ_TIMEOUT,TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(CONNECT_TIMEOUT,TimeUnit.SECONDS)//设置连接超时时间
                .build();
		
		public static void firstClick(int courseID) {
	        try {
	        	Request request = new Request.Builder()
        			  .url("http://acadol.hnu.edu.cn/LMS/study/course/onlineCourse/study.do?id="+courseID)
        			  .get()
        			  .addHeader("upgrade-insecure-requests", "1")
        			  .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.117 Safari/537.36")
        			  .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
        			  .addHeader("referer", "http://acadol.hnu.edu.cn/LMS/study/course/onlineCourse/detail.do?id="+courseID)
        			  .addHeader("accept-encoding", "gzip, deflate")
        			  .addHeader("accept-language", "en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7")
        			  //.addHeader("cookie", "cookie_staff_password=\"\"; cookie_staff_loginName=201508010606; JSESSIONID=DDCFC6AA8A40EEC2B962D32E784A4C5B")
        			  .build();


	        	Response response = client.newCall(request).execute();
				if (response.code()==302||response.code()==200) {
					System.out.println("初次点击成功 ");
	            } else {
	            	System.out.println("初次点击失败 Status Code:"+response.code());
	            }
			} catch (UnknownHostException ue) {
				error("错误","网络未连接");
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public static void login(String loginName,String password,String code,MainFrame mainFrame) {
			if (loginName.length()==0||password.length()==0||code.length()==0) {
				error("错误","输入不能为空");
			} else {
		        try {
		        	FormBody body = new FormBody.Builder()
		        		    .add("msgType", "")
		        		    .add("redirectUrl", "")
		        		    .add("txt_code", code)
		        		    .add("txt_loginName", loginName)
		        		    .add("txt_password", password)
		        		    .build();
	
		        	Request request = new Request.Builder()
		        	  .url("http://acadol.hnu.edu.cn/LMS/doLogin.do")
		        	  .post(body)
		        	  .addHeader("origin", "http://acadol.hnu.edu.cn")
		        	  .addHeader("upgrade-insecure-requests", "1")
		        	  .addHeader("content-type", "application/x-www-form-urlencoded")
		        	  .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.117 Safari/537.36")
		        	  .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
		        	  .addHeader("referer", "http://acadol.hnu.edu.cn/LMS/doLogin.do")
		        	  .addHeader("accept-encoding", "gzip, deflate")
		        	  .addHeader("accept-language", "en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7")
		        	  //.addHeader("cookie", "JSESSIONID="+JSESSIONID)
		        	  .build();
	
		        	Response response = client.newCall(request).execute();
		        	//System.out.println(response.body().string());
		        	//if (!response.isSuccessful()) error("错误","网络未连接");
					if (response.code()==302||response.code()==200) {
						if (response.body().string().indexOf("验证码")==-1) {
							mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);// 设置主窗体的关闭方式
							mainFrame.setVisible(true);// 使主窗体可见
						}
						else {
							error("错误", "<html>登录失败 Σ( ° △ °|||)︴ <br>请检查密码和验证码是否正确</html>");
						}
		            } else {
		            	error("错误","登录失败 Σ( ° △ °|||)︴ Status Code:"+response.code());
		            }
				} catch (UnknownHostException ue) {
					error("错误","网络未连接");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		public static void send() {
			Random rand = new Random();
			int ihour=1;
			int imin=rand.nextInt(60);
			int is=rand.nextInt(60);
			String hour=String.format("%02d",ihour);
			String min=String.format("%02d",imin);
			String s=String.format("%02d",is);
			Iterator<String> i = hash.iterator();
			while (i.hasNext()) {
				String id = i.next();
				try {//beforeunload
					Request request = new Request.Builder()
							.url(ip+"/coursePlayer/loopCommit.do?student_id=" + student_id
									+ "&lesson_location=beforeunload&lesson_status=completed&score=10.0&suspend_data=&session_time="+hour+"%3A"+min+"%3A"+s+"&lesson_progress=0.0&masteryscore=&total_time=7&required_time=3&session_id="
									+ id)
							.get().addHeader("accept", "*/*").addHeader("x-requested-with", "XMLHttpRequest")
							.addHeader("user-agent",
									"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.117 Safari/537.36")
							.addHeader("referer",ip+"/coursePlayer/open.do?sessionId=" + id)
							.addHeader("accept-encoding", "gzip, deflate")
							.addHeader("accept-language", "en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7")
							//.addHeader("cookie", "course_storage=" + course_storage + "; course_storage=" + course_storage)
							.build();
	
					Response response = client.newCall(request).execute();
					if (response.code()==302||response.code()==200) {
						System.out.println("刷课成功");
		            } else {
		            	System.out.println("刷课失败");
		            }
				} catch (UnknownHostException ue) {
					error("错误","网络未连接");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		public static void downloadCode() {
			
			InputStream is = null;
			byte[] buf = new byte[2048];
			int len = 0;
			FileOutputStream fos = null;
	
			Response response = null;
			try {
				Request request = new Request.Builder()
						.url("http://acadol.hnu.edu.cn/code?r=0.44931821775006675?r=0.8252846830743397?r=0.11334797887262393?r=0.9508558000660963")
						.build();

				response = client.newCall(request).execute();
				if (response.code()==200) {
					System.out.println("验证码下载成功");
	            } else {
	            	System.out.println("验证码下载失败 Status Code:"+response.code());
	            }
			} catch (UnknownHostException ue) {
				error("错误","网络未连接");
			}
			catch (IOException e) {
				e.printStackTrace();
			}

			// 储存下载文件的目录
			File dir = new File("/res");//"./src/res"
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(dir, "code.jpg");

			try {
				is = response.body().byteStream();
				fos = new FileOutputStream(file);
				while ((len = is.read(buf)) != -1) {
					fos.write(buf, 0, len);
				}
				fos.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (is != null) {
						is.close();
					}
					if (fos != null) {
						fos.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		public static void getCourseStorage(int courseID) {
			List<CourseInfo> courseInfoList = parseJSON();
			for (CourseInfo ci:courseInfoList) {
		        try {
		        	FormBody body = new FormBody.Builder()
		        		    .add("courseId", ci.companyRelationCourseId)
		        		    .add("courseNumer", "")
		        		    .add("courseType", ci.type)
		        		    .add("href", ci.href)
		        		    .add("id", ci.chapterId)
		        		    .add("location", ci.location)
		        		    .add("requiredTime", ci.finishLength)
		        		    .add("staffChapterId", ci.id)
		        		    .add("status", ci.status)
		        		    .add("suspeDate", ci.suspendData)
		        		    .add("totalTime", ci.totalTime)
		        		    .add("url", urlinit)
		        		    .build();
	
		        	Request request = new Request.Builder()
	        			  .url("http://acadol.hnu.edu.cn/LMS/ajax/coursePlayer/onlineCoursePlayer.do")
	        			  .post(body)
	        			  .addHeader("accept", "*/*")
	        			  .addHeader("origin", "http://acadol.hnu.edu.cn")
	        			  .addHeader("x-requested-with", "XMLHttpRequest")
	        			  .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.117 Safari/537.36")
	        			  .addHeader("content-type", "application/x-www-form-urlencoded")
	        			  .addHeader("referer", "http://acadol.hnu.edu.cn/LMS/coursePlayer/player.do?id="+courseID)
	        			  .addHeader("accept-encoding", "gzip, deflate")
	        			  .addHeader("accept-language", "en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7")
	        			  //.addHeader("cookie", "cookie_staff_password=\"\"; cookie_staff_loginName=201509010309; JSESSIONID=4CE8E53CC843670CE37876DECA38181B")
	        			  .build();
		        	
		        	Response response = client.newCall(request).execute();
					if (response.code()==200) {
						hash.add(response.body().string());
						System.out.println("获取course_storage成功");
		            } else {
		            	System.out.println("获取course_storage失败 Status Code:"+response.code());
		            }
				} catch (UnknownHostException ue) {
					error("错误","网络未连接");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		public static void getCourseInfoList(int courseID) {
	        try {
	        	FormBody body = new FormBody.Builder()
	        		    .add("id", ""+courseID)
	        		    .build();

	        	Request request = new Request.Builder()
        			  .url("http://acadol.hnu.edu.cn/LMS/ajax/coursePlayer/getStaffScoList.do")
        			  .post(body)
        			  .addHeader("accept", "*/*")
        			  .addHeader("origin", "null")
        			  .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.117 Safari/537.36")
        			  .addHeader("content-type", "application/x-www-form-urlencoded")
        			  .addHeader("accept-encoding", "gzip, deflate")
        			  .addHeader("accept-language", "en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7")
        			  .build();

	        	Response response = client.newCall(request).execute();
				if (response.code()==200) {
					System.out.println("获取课程信息列表成功");
					courseInfoStr=response.body().string();
	            } else {
	            	System.out.println("获取课程信息列表失败 Status Code:"+response.code());
	            }
			} catch (UnknownHostException ue) {
				error("错误","网络未连接");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public class ScoList {
			private String scoList;
			public String getScoList() {
				return scoList;
			}
			public void setScoList(String s) {
				scoList=s;
			}
		}
		
		public class CourseInfo {
			public String id;
			public String companyRelationCourseId;
			public String title;
			public String href;
			public String type;
			public String location;
			public String suspendData;
			public String status;
			public String progress;
			public String score;
			public String finishLength;
			public String totalTime;
			public String chapterId;
		}
		
		public static List<CourseInfo> parseJSON() {
			Gson gson =new Gson();
			ScoList scoList=gson.fromJson(courseInfoStr, ScoList.class);
			String sco=scoList.getScoList();
			List<CourseInfo> courseInfoList=gson.fromJson(sco, new TypeToken<List<CourseInfo>>(){}.getType());
			return courseInfoList;
		}
		
		
		public static void getStudentId(int courseID) {
			
			
	        try {
	        	FormBody body = new FormBody.Builder()
	        		    .add("courseId", ""+courseID)
	        		    .build();

	        	Request request = new Request.Builder()
        			  .url("http://acadol.hnu.edu.cn/LMS/ajax/site/coursenotes/answer_player.do")
        			  .post(body)
        			  .addHeader("accept", "application/json, text/javascript, */*; q=0.01")
        			  .addHeader("origin", "http://acadol.hnu.edu.cn")
        			  .addHeader("x-requested-with", "XMLHttpRequest")
        			  .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.117 Safari/537.36")
        			  .addHeader("content-type", "application/x-www-form-urlencoded")
        			  .addHeader("referer", "http://acadol.hnu.edu.cn/LMS/coursePlayer/player.do?id="+courseID)
        			  .addHeader("accept-encoding", "gzip, deflate")
        			  .addHeader("accept-language", "en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7")
        			  //.addHeader("cookie", "cookie_staff_loginName=201509010309; cookie_staff_password=\"\"; JSESSIONID=287CB03D1E1E97F3BF945A4610E267BD")
        			  .build();

	        	Response response = client.newCall(request).execute();
				if (response.code()==200) {
					System.out.println("获取student_id成功");
					Gson gson =new Gson();
					String temp=response.body().string();
					student_id=temp.substring(temp.lastIndexOf(":")+1,temp.length()-1).trim();
					
					System.out.println("student_id:"+student_id);
	            } else {
	            	System.out.println("获取student_id失败 Status Code:"+response.code());
	            }
			} catch (UnknownHostException ue) {
				error("错误","网络未连接");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public static void haveClass(int courseID) {
			hash.clear();
			firstClick(courseID);
			getCourseInfoList(courseID);
			getCourseStorage(courseID);
			getStudentId(courseID);
			send();
		}
		
		public static class notify {
			public void CreateJFrame(String title,String content)
			{
				int width = Toolkit.getDefaultToolkit().getScreenSize().width;
			    int height = Toolkit.getDefaultToolkit().getScreenSize().height;
			    // 定义窗体的宽高
			    int windowsWedth = 300;
			    int windowsHeight = 300;
				
			    JFrame jf = new JFrame(title);//窗口标题
			    Container container = jf.getContentPane();//创建容器
			    JLabel jl = new JLabel(content);
			    //使标签上的文字居中
			    jl.setHorizontalAlignment(SwingConstants.CENTER);
			    jl.setFont(new java.awt.Font("微软雅黑",0,20));
			    container.add(jl);//添加上面的组件，不添加就显示不到
			    container.setBackground(Color.WHITE);
			    jf.setVisible(true);//窗口可视
			    jf.setBounds((width - windowsWedth) / 2,(height - windowsHeight) / 2, windowsWedth, windowsHeight);

			}
		}
		
		public static void error(String title,String content) {
			new notify().CreateJFrame(title,content);
		}
		
		public static void toast(String title,String content) {
			new notify().CreateJFrame(title,content);
		}
		
		
	}
}
