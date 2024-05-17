package dad.LaLagunaUrbanApp.Bd;

public class usuario {

	private int id_usuario;
	private String nombre;
	private int edad;
	private Double peso;
	private int altura;
	private String sexo;
	private String estado;
	private String objetivo;
	private String avatar = "1";
	private String password;
	private String nombre_user;
	
	public void setUsuario(int idUsuario, String nombre, int edad, Double peso, int altura, String sexo, String estado, String objetivo, String avatar, String password, String nombreUsuario) {
	    this.id_usuario = idUsuario;
	    this.nombre = nombre;
	    this.edad = edad;
	    this.peso = peso;
	    this.altura = altura;
	    this.sexo = sexo;
	    this.estado = estado;
	    this.objetivo = objetivo;
	    this.avatar = avatar;
	    this.password = password;
	    this.nombre_user = nombreUsuario;
	}
	
	public int getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	public int getAltura() {
		return altura;
	}
	public void setAltura(int altura) {
		this.altura = altura;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getObjetivo() {
		return objetivo;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNombre_user() {
		return nombre_user;
	}
	public void setNombre_user(String nombre_user) {
		this.nombre_user = nombre_user;
	}
	
	public Double obtenerPorcentajeGraso(String sexo, String estadoObjetivo) {
		Double porcentaje = 0.0;
		
		if(sexo.equals("hombre")) {
			switch(estadoObjetivo) {
		    	case "1":
					porcentaje = Math.ceil((12 + 15) / 2.0);
		    		break;
		    	case "2":
					porcentaje = Math.ceil((15 + 19) / 2.0);
		    		break;
		    	case "3":
					porcentaje = Math.ceil((20 + 40) / 2.0);
		    		break;
		    	case "4":
					porcentaje = Math.ceil((10 + 12) / 2.0);
		    		break;
		    	case "5":
					porcentaje = Math.ceil((8 + 10) / 2.0);
		    		break;
		    	case "6":
					porcentaje = Math.ceil((5 + 8) / 2.0);		    		break;
			}

		}else {
	        switch(estadoObjetivo) {
	            case "1":
	                porcentaje = Math.ceil((25 + 30) / 2.0);
	                break;
	            case "2":
	                porcentaje = Math.ceil((30 + 35) / 2.0);
	                break;
	            case "3":
	                porcentaje = Math.ceil((35 + 45) / 2.0);
	                break;
	            case "4":
	                porcentaje = Math.ceil((20 + 25) / 2.0);
	                break;
	            case "5":
	                porcentaje = Math.ceil((15 + 20) / 2.0);
	                break;
	            case "6":
	                porcentaje = Math.ceil((5 + 15) / 2.0);
	                break;
	        } 
		}
		return porcentaje;
	}
	
	public String getimagenAvatar() {

		String imagen = "/avatares/avatar1.png";
        
		switch (avatar) {
        case "1":
    		imagen = "/avatares/avatar1.png";
            break;
        case "2":
    		imagen = "/avatares/avatar2.png";
            break;
        case "3":
    		imagen = "/avatares/avatar3.png";
            break;
        case "4":
    		imagen = "/avatares/avatar4.png";
            break;
        case "5":
    		imagen = "/avatares/avatar5.png";
            break;
        case "6":
    		imagen = "/avatares/avatar6.png";
            break;
        case "7":
    		imagen = "/avatares/avatar7.png";
            break;
        case "8":
    		imagen = "/avatares/avatar8.png";
            break;
        case "9":
    		imagen = "/avatares/avatar9.png";
            break;
        case "10":
    		imagen = "/avatares/avatar10.png";
            break;
        default:
            break;
		}
		return imagen;
	}
}
