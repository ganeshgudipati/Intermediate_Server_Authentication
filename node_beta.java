import java.io.*;
import java.net.*;
import java.util.Scanner;
class obj
{
	String req_nodename=" ";
	int port_number=0;
}
class node_beta
{
		public ServerSocket ss=null;
		public Socket s=null;
		public DataInputStream din=null;
		public DataOutputStream dout= null;
		public BufferedReader br=null;
		public String str="";
		public String str2="";
		public int num1=0;
		
	public void newuser(int port)
	{
		try
		{
		s=new Socket("localhost",port);
		din=new DataInputStream(s.getInputStream());
		dout= new DataOutputStream(s.getOutputStream());
		br=new BufferedReader(new InputStreamReader(System.in));
		str="";
		str2="";
		    str=din.readUTF();				//1st readUTF(info)
			System.out.println(str);
			str=din.readUTF();				//2nd readUTF(username:)
			System.out.println(str);
			str2=br.readLine();				
			dout.writeUTF(str2);			//1st writeUTF(username)
			str=din.readUTF();				//3nd readUTF(username exists or not)
			System.out.println(str);
			while(str.equals("Username already exists:("))
			{		
				str=din.readUTF();				//4th readUTF(username:)
				System.out.println(str);
				str2=br.readLine();				
				dout.writeUTF(str2);			//2st writeUTF(username)
				str=din.readUTF();				//5th readUTF(username exists or not)
				System.out.println(str);
			}
			str=din.readUTF();				//6threadUTF(macid:)
			System.out.println(str);
			str2=br.readLine();				
			dout.writeUTF(str2);			//writeUTF
			str=din.readUTF();				//readUTF
			System.out.println(str);
			while(str.equals("Multiple users not allowed for single MAC_id"))
			{		
				str=din.readUTF();				//readUTF
				System.out.println(str);
				str2=br.readLine();				
				dout.writeUTF(str2);			//writeUTF
				str=din.readUTF();				//readUTF
				System.out.println(str);
			}	
			str=din.readUTF();
			System.out.println(str);
			str2=br.readLine();
			dout.writeUTF(str2);
			str=din.readUTF();
			System.out.println(str);
			dout.close();
			s.close();
		}
		catch(ConnectException c)
		{
			System.out.println("[Server is offline]");
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
	
	public void login(int port, obj o1, String id)
	{
		class inner{
		public boolean check_user(String user, String id)
		{
				boolean found=false;Scanner x;
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
						if(mac_id.equals(id))
						{
							if(u_name.equals(user))
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
		}}
		try
		{
		s=new Socket("localhost",port);
		din=new DataInputStream(s.getInputStream());
		dout= new DataOutputStream(s.getOutputStream());
		br=new BufferedReader(new InputStreamReader(System.in));
		str="";
		str2="";boolean f=false;
		inner i1=new inner();
			str=din.readUTF();							//User credentials Required
			System.out.println(str);
			str=din.readUTF();						    //Enter your Username here:
			while(f==false)
			{
				System.out.println(str);
			str2=br.readLine();
			f=i1.check_user(str2,id);
			if(f==false)
			{
				System.out.println("Your input userid doesn't match your macid!!!");
			}
			}
			dout.writeUTF(str2);						//Writing User name
			str=din.readUTF();							//enter password
			System.out.println(str);
			str2=br.readLine();				
			dout.writeUTF(str2);						//writing password
			str=din.readUTF();							//authentication succes or fail		
			System.out.println(str);
			if(str.equals("Authentication successful"))
			{
				str2=din.readUTF();
				System.out.println(str2);
				str=br.readLine();
				dout.writeUTF(str);
				o1.req_nodename=str;
				str2=din.readUTF();
				System.out.println(str2);
				str2=din.readUTF();
				num1=Integer.parseInt(str2);
				o1.port_number=num1;
				dout.close();
				s.close();
			}
			else
			{
				num1=0;
				o1.port_number=num1;
				o1.req_nodename=" ";
				dout.close();
				s.close();
			}
			
		}
		catch(ConnectException c)
		{
			System.out.println("[Server is offline]");
		}
		catch(UnknownHostException u)
		{
			System.out.println(u);
		}
		catch(IOException i)
		{
			System.out.println(i);
		}
		//return num1;
	}
	
	public void connect_node(obj o1)
	{
		try
		{
		System.out.println("Waiting for node1 to connect...");
		ss=new ServerSocket(o1.port_number);
		s =ss.accept();
		din=new DataInputStream(s.getInputStream());
		dout= new DataOutputStream(s.getOutputStream());
		br=new BufferedReader(new InputStreamReader(System.in));
		str="";
		str2="";
		str=din.readUTF();
		System.out.println(str);
		if(str.equals("Connected:)"))
		{
			while(!str.equals("EXIT"))
			{
				str2=br.readLine();
				dout.writeUTF(str2);
				str=din.readUTF();
				System.out.println(o1.req_nodename+" :"+str);
			}
			din.close();
			s.close();
			ss.close();
		}
		else
		{	
			din.close();
			s.close();
			ss.close();
		}
		}
		catch(SocketException s)
		{
			System.out.println("["+o1.req_nodename+" went offline]");
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
	public boolean mac(String id)
	{
		boolean found=false;Scanner x;
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
						if(mac_id.equals(id))
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
		
	
	public static void main(String[] args) throws Exception
	{
		obj o1=new obj();
		node_beta n=new node_beta();
		int n1=0;boolean f=false;
		System.out.println("Enter a macid for this terminal:");
		Scanner scanner = new Scanner(System.in);
		String i = scanner.nextLine();
		f=n.mac(i);
		if(f==false)
		{
			n.newuser(2000);
		}
		else
		{
			n.login(3000,o1,i);
		}
		if(o1.port_number!=0)
		{	
			n.connect_node(o1);
		}
			
	}
}