import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Random;
class obj
{
	String req_node=" ";
	int random_port=0;
	int waiting_port=1;
}
class server
{	
			public ServerSocket ss2=null;
			public Socket s2=null;
			public DataInputStream din2=null;
			public DataOutputStream dout2=null;
			public BufferedReader br=null;
			public String str="",str2="",str3="";
			public int num1=0,num2=0;
	public void authentication(int port,obj oo)
	{
		class verify
		{
			
			boolean readpass(String u,String p, String path)
			{
				boolean found=false;Scanner x;
				String u_name=" ",mac_id=" ",pword=" ",date=" ",time=" ",w_port=" ",e=" ";
				try
				{
					x=new Scanner(new File(path));
					x.useDelimiter("[,\n]");
					while(x.hasNext() && !found)
					{
						u_name=x.next();
						mac_id=x.next();
						pword=x.next();
						date=x.next();
						time=x.next();
						w_port=x.next();
						e=x.next();
						if(u_name.equals(u))
						{
							if(pword.equals(p))
							{
								found=true;
							}
						}
					}
					
				}	
				catch(Exception ex)
				{
					System.out.println(ex);
				}
				return found;
			}
			boolean finduser(String u, String path)
			{
				boolean found=false;Scanner x;
				String u_name=" ",mac_id=" ",pword=" ",date=" ",time=" ",w_port=" ",e=" ";
				try
				{
					x=new Scanner(new File(path));
					x.useDelimiter("[,\n]");
					while(x.hasNext() && !found)
					{
						u_name=x.next();
						mac_id=x.next();
						pword=x.next();
						date=x.next();
						time=x.next();
						w_port=x.next();
						e=x.next();
						if(u_name.equals(u))
						{
							found=true;
						}
					}
					
				}	
				catch(Exception ex)
				{
					System.out.println(ex);
				}
				return found;
			}
			int findport(String u)
			{
				boolean found=false;Scanner x;int pnumber=0;
				String u_name=" ",mac_id=" ",pword=" ",date=" ",time=" ",w_port=" ",e=" ";
				try
				{
					x=new Scanner(new File("database.txt"));
					x.useDelimiter("[,\n]");
					while(x.hasNext() && !found)
					{
						u_name=x.next();
						mac_id=x.next();
						pword=x.next();
						date=x.next();
						time=x.next();
						w_port=x.next();
						e=x.next();
						if(u_name.equals(u))
						{
							pnumber=Integer.parseInt(w_port);
						}
					}
					
				}	
				catch(Exception ex)
				{
					System.out.println(ex);
				}
				return pnumber;
			}
		}
		try{
			ss2=new ServerSocket(port);
			s2=ss2.accept();
			din2=new DataInputStream(s2.getInputStream());
			dout2= new DataOutputStream(s2.getOutputStream());
			str="";str2="";str3="";
			String username="",passwd="";
			verify v1=new verify();
			boolean correct=false;
			str="User credentials Required";
			dout2.writeUTF(str);
			str="Enter your Username here:";
			dout2.writeUTF(str);
			username=din2.readUTF();											//reading the username
			System.out.println("Username:"+username);
			oo.req_node=username;
			dout2.writeUTF("Enter your password:");
			passwd=din2.readUTF();												//reading the password
			correct=v1.readpass(username,passwd,"database.txt");
			System.out.println("verifying credentials...");
			int low=5001;
			int high=50000;
			Random r=new Random();
			int result=0;
			String re=" ";
			boolean findu=false;
			
			if(correct==true)
			{
				str="Authentication successful";
				dout2.writeUTF(str);
				System.out.println(str);
				dout2.writeUTF("enter node to connect");
				str2=din2.readUTF();
				System.out.println(username+" wants to connect to "+ str2);
				findu=v1.finduser(str2,"database.txt");
				if(findu==false)
				{
					dout2.writeUTF("No such user exists");
					oo.random_port=0;
					oo.waiting_port=1;
					dout2.writeUTF("0");
					din2.close();
					s2.close();
					ss2.close();
				}
				else
				{
				dout2.writeUTF("Please wait for "+str2+" to get connected");
				result = r.nextInt(high-low) + low;
				re=Integer.toString(result);
				oo.random_port=result;
				oo.waiting_port=v1.findport(str2);
				dout2.writeUTF(re);
				din2.close();
				s2.close();
				ss2.close();
				}
			}
			else
			{
				dout2.writeUTF("Authentication failure");
				System.out.println("Authentication failure");
				oo.random_port=0;
				oo.waiting_port=1;
				din2.close();
				s2.close();
				ss2.close();
			}
			
		}
			catch(UnknownHostException u)
			{
				System.out.println(u);
			}
			catch(IOException i)
			{
				
				System.out.println(i);
			}
			
	}
		public void certi_creation(int port)
		{
			try{
			ss2=new ServerSocket(port);
			s2=ss2.accept();
			din2=new DataInputStream(s2.getInputStream());
			dout2= new DataOutputStream(s2.getOutputStream());
			str="";
			}
			catch(UnknownHostException u)
			{
				System.out.println(u);
			}
			catch(IOException i)
			{
				System.out.println(i);
			}
			class wcsv1
			{
			void saverecord(String username, String macid, String password,String dateandtime, String waiting_port, String path)
			{
				try
				{
					FileWriter fw = new FileWriter(path,true);
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter pw = new PrintWriter(bw);
					pw.println(username+","+macid+","+password+","+dateandtime+","+waiting_port+","+"end");
					pw.flush();
					pw.close();
				}
				catch(Exception ex)
				{
					System.out.println(ex);
				}
			}
			void savecertificate(String username, String macid, String dateandtime, String waiting_port, String path)
			{
				try
				{
					FileWriter fw = new FileWriter(path,true);
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter pw = new PrintWriter(bw);
					pw.println("Username,MACid,date,time,waiting_port,end");
					pw.println(username+","+macid+","+dateandtime+","+waiting_port+","+"end");
					pw.flush();
					pw.close();
				}
				catch(Exception ex)
				{
					System.out.println(ex);
				}
			}
			boolean readu(String element, String path)
			{
				boolean found=false;Scanner x;
				String u_name=" ",mac_id=" ",pword=" ",date=" ",time=" ",w_port=" ",e=" ";
				try
				{
					x=new Scanner(new File(path));
					x.useDelimiter("[,\n]");
					while(x.hasNext() && !found)
					{
						u_name=x.next();
						mac_id=x.next();
						pword=x.next();
						date=x.next();
						time=x.next();
						w_port=x.next();
						e=x.next();
						if(u_name.equals(element))
						{
							found=true;
						}
					}
					
				}	
				catch(Exception ex)
				{
					System.out.println(ex);
				}
				return found;
			}
			boolean readm(String element, String path)
			{
				boolean found=false;Scanner x;
				String u_name=" ",mac_id=" ",pword=" ",date=" ",time=" ",w_port=" ",e=" ";
				try
				{
					x=new Scanner(new File(path));
					x.useDelimiter("[,\n]");
					while(x.hasNext() && !found)
					{
						u_name=x.next();
						mac_id=x.next();
						pword=x.next();
						date=x.next();
						time=x.next();
						w_port=x.next();
						e=x.next();
						if(mac_id.equals(element))
						{
							found=true;
						}
					}
					
				}	
				catch(Exception ex)
				{
					System.out.println(ex);
				}
				return found;
			}
			boolean readp(String element, String path)
			{
				boolean found=false;Scanner x;
				String u_name=" ",mac_id=" ",pword=" ",date=" ",time=" ",w_port=" ",e=" ";
				try
				{
					x=new Scanner(new File(path));
					x.useDelimiter("[,\n]");
					while(x.hasNext() && !found)
					{
						u_name=x.next();
						mac_id=x.next();
						pword=x.next();
						date=x.next();
						time=x.next();
						w_port=x.next();
						e=x.next();
						if(w_port.equals(element))
						{
							found=true;
						}
					}
					
				}	
				catch(Exception ex)
				{
					System.out.println(ex);
				}
				return found;
			}
		}
			try
			{
				wcsv1 ww=new wcsv1();
				String username="",macid="",passwd="";
				boolean f=true,f2=true,f3=true;
				str="Since you are a new user you need to provide your details for creating a text certificate";
				dout2.writeUTF(str);						//1st writeUTF(info)
				dout2.writeUTF("Enter a valid Username");		//2nd writeUTF(username:)
			while(f==true)
			{
			username=din2.readUTF();						//1st readUTF(username)
			f=ww.readu(username,"database.txt");
			if(f==true)
			{
				dout2.writeUTF("Username already exists:(");				//3rd(warning)
				dout2.writeUTF("Please try with a different Username");	//4th(info)
			}
			}
			dout2.writeUTF("Username available:)");
			System.out.println("New Username: "+username);
			dout2.writeUTF("Enter your MAC_id:");
			while(f2==true)
			{
			macid=din2.readUTF();
			f2=ww.readm(macid,"database.txt");
			if(f2==true)
			{
				dout2.writeUTF("Multiple users not allowed for single MAC_id");
				dout2.writeUTF("Go with different MAC_id if you want to create a New User");
			}
			}
			dout2.writeUTF("MAC_id registred");
			System.out.println("MAC_ID: "+macid);
			dout2.writeUTF("Enter a Password:");
			passwd=din2.readUTF();
			System.out.println("Creating a certificate for the new node...");
			String file=" ";
			file=username+".txt";
			Random r = new Random();
			int low=2000;
			int high=5000;
			int result;
			String re=" ";
			while(f3==true)
			{
			 result = r.nextInt(high-low) + low;
			 re=Integer.toString(result);
			 f3=ww.readp(re,"database.txt");
			}
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy,HH:mm:ss");  
			Date date = new Date();  
			String dandt=formatter.format(date);
			ww.saverecord(username,macid,passwd,dandt,re,"database.txt");
			ww.savecertificate(username,macid,dandt,re,file);
			System.out.println("Created successfully");
			dout2.writeUTF("Created successfully");
			din2.close();
			s2.close();
			ss2.close();
			}
			catch(UnknownHostException u)
			{
				System.out.println(u);
			}
			catch(IOException i)
			{
				System.out.println(i);
			}
			
		}
		public int request_node(int port,int n2,String req_user)
		{
			try
			{
			s2=new Socket("localhost",port);
			din2=new DataInputStream(s2.getInputStream());
			dout2= new DataOutputStream(s2.getOutputStream());
			br=new BufferedReader(new InputStreamReader(System.in));
			str="";
			str2="";
			str=req_user;
			dout2.writeUTF(str);
			str2=din2.readUTF();
			if(str2.equals("y"))
			{	
				num2=n2;
				System.out.println("Request accepted...");
				dout2.writeUTF(String.valueOf(n2));
				dout2.close();
				s2.close();
			}
			else
			{
				num2=0;
				System.out.println("Request rejected...");
				dout2.close();
				s2.close();
			}
			}
			catch(ConnectException c)
			{
			System.out.println("[Requested user is offline]");
			num2=0;
			}
			catch(UnknownHostException u)
			{
				System.out.println(u);
			}
			catch(IOException i)
			{
				System.out.println(i);
			}
			return num2;
		}
			
		public void reject(int port)
		{
			try
			{
			s2=new Socket("localhost",port);
			din2=new DataInputStream(s2.getInputStream());
			dout2= new DataOutputStream(s2.getOutputStream());
			br=new BufferedReader(new InputStreamReader(System.in));
			str="";
			str2="";
			dout2.writeUTF("Connection rejected!!!");
			dout2.close();
			s2.close();
			}
			catch(ConnectException c)
			{
			System.out.println("[User is offline]");
			}
			catch(UnknownHostException u)
			{
				System.out.println(u);
			}
			catch(IOException i)
			{
				System.out.println(i);
			}
		}
		
	public static void main(String[] args) throws Exception
	{
		server n=new server();
		obj ob=new obj();
		int n1=0,n2=1;
		String i=" ";
		System.out.println("This is the Main Server");
		Scanner scanner = new Scanner(System.in);
		while(!i.equals("3"))
		{ob.random_port=0;ob.req_node=" ";
		n1=0;n2=1;
		System.out.println("1.Go to new userpage 2.See connection Requests 3.exit");
		i = scanner.nextLine();
		if(i.equals("1"))
		{
			n.certi_creation(2000);
		}
		else if(i.equals("2"))
		{
			n.authentication(3000,ob);
		}
		if(ob.random_port!=0)
		{
			n2=n.request_node(ob.waiting_port,ob.random_port,ob.req_node);
		}
		if(n2==0)
		{
			n.reject(ob.random_port);
		}
		}
	}
}
	