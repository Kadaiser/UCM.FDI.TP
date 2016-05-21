package es.ucm.fdi.tp.Practica6.buffer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * Buffer de comunicacion cliente servidor.
 * Esta clase actua a modo de structura para un tipo de dato que permite realizar llamadas
 * por metodos a un socket, permitiendo enviarlo y recibirlo, asi como sacar/introducir contenido 
 * del mismo
 * @author Kadaiser
 *
 */
public class Connection {

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	/**
	 * <b>Connection</b>
	 * <p> Constructor de clase connection almacenar los streams de entrada y salida en atributos</p>
	 * @param socket
	 * @throws IOException
	 */
	public Connection(Socket socket) throws IOException{
		this.socket = socket;
		this.out = new ObjectOutputStream(this.socket.getOutputStream());
		this.in = new ObjectInputStream(this.socket.getInputStream());
	}
	
	/**
	 * <b>sendObject</b>
	 * <p>Procedimeinto de envio de objetos</p>
	 * @param r
	 * @param piece 
	 * @param gameFactory 
	 * @throws IOException
	 */
	public void sendObject(Object r) throws IOException{
		out.writeObject(r);
		out.flush();
		out.reset();
	}
	
	/**
	 * <b>sendException</b>
	 * <p>Procedimeinto de envio de objetos</p>
	 * @param e
	 * @param piece 
	 * @param gameFactory 
	 * @throws IOException
	 */
	public void sendException(Exception e) throws IOException{
		out.writeObject(e);
		out.flush();
		out.reset();
	}
	
	/**
	 * <b>getObject</b>
	 * <p>Procedimiento de recepcion de objetos</p>
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public Object getObject() throws IOException, ClassNotFoundException{
		return in.readObject();
		
	}
	
	/**
	 * <b>stop</b>
	 * <p>Procedimiento de cierre de un canal de comunicacion</p>
	 * @throws IOException
	 */
	public void stop() throws IOException{
		this.socket.close();
	}
	
	/**
	 * <b>getPort</b>
	 * <p>Consulta de valor del puerto para una conexion</p>
	 * @return int with port number
	 */
	public int getPort(){
		return this.socket.getPort();
	}

}
