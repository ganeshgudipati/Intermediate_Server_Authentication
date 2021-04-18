import java.io.*;
import java.net.*;
import java.util.Scanner;
class obj2{
	String req_node=" ";
	int ran_port=0;
}
class receiver
{
		public ServerSocket ss=null;
		public Socket s=null;
		public DataInputStream din=null;
		public DataOutputStream dout= null;
		public BufferedReader br=null;
		public String str="";
		public String str2="";
		public int num1=0;
		
		public void request(int port, obj2 o2)
		{
			try
			{
				ss=new ServerSocket(port);
				s =ss.accept();
				din=new DataInputStream(s.getInputStream());
				dout= new DataOutputStream(s.getOutputStream());
				br=new BufferedReader(new InputStreamReader(System.in));
				str="";str2="";
				str=din.readUTF();
				o2.req_node=str;
				System.out.println("You got a connection request from "+str+" Do you want to accept[y/n]");
				str2=br.readLine();
				dout.writeUTF(str2);
				if(str2.equals("y"))
				{
					System.out.println("Request accepted...");
					str=din.readUTF();
					num1=Integer.parseInt(str);
					o2.ran_port=num1;
					din.close();
					s.close();
					ss.close();
					
				}
				else
				{
					System.out.println("Request rejected...");
					num1=0;
					o2.ran_port=num1;
					o2.req_node=" ";
					din.close();
					s.close();
					ss.close();
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
			//return num1;
		}
		public void communicate(obj2 o2)
		{
			try
			{
			System.out.println("You are now connected to "+o2.req_node);
			s=new Socket("localhost",o2.ran_port);
			din=new DataInputStream(s.getInputStream());
			dout= new DataOutputStream(s.getOutputStream());
			br=new BufferedReader(new InputStreamReader(System.in));
			str="";str2="";
			dout.writeUTF("Connected:)");
			while(!str2.equals("EXIT"))
			{
				str=din.readUTF();
				System.out.println(o2.req_node+":"+str);
				str2=br.readLine();
				dout.writeUTF(str2);
			}
			dout.close();
			s.close();
			}
			catch(ConnectException c)
			{
			System.out.println("["+o2.req_node+" went offline]");
			}
			catch(SocketException s)
			{
			System.out.println("["+o2.req_node+" went offline]");
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
		int getport(String element, String path)
			{
				boolean found=false;
				int waiting_port=0;Scanner x;
				String u_name=" ",mac_id=" ",date=" ",time=" ",w_port=" ",e=" ";
				try
				{
					x=new Scanner(new File(path));
					x.useDelimiter("[,\n]");
					while(x.hasNext() && !found)
					{
						u_name=x.next();
						mac_id=x.next();
						date=x.next();
						time=x.next();
						w_port=x.next();
						e=x.next();
						if(u_name.equals(element))
						{
							found=true;
						}
					}
					if(found==false)
					{
						waiting_port=0;
					}
					else
					{
						int w=Integer.parseInt(w_port); 
						waiting_port=w;
					}
				}	
				catch(FileNotFoundException f)
				{
				}
				catch(Exception ex)
				{
					System.out.println(ex);
				}
				return waiting_port;
			}
			
		public static void main(String args[])
		{
			obj2 o2=new obj2();
			receiver n=new receiver();
			int n1=0;
			System.out.print("Enter a username:");
			Scanner scanner = new Scanner(System.in);
			String i = scanner.nextLine();
			String path=i+".txt";
			int waiting_port=n.getport(i,path);
			if(waiting_port==0)
			{
				System.out.println("No such user exists!!!");
			}
			else
			{
				System.out.println("Checking for connection requests...");
				n.request(waiting_port,o2);
				if(o2.ran_port!=0)
				{
					n.communicate(o2);
				}
			}
		}
}	