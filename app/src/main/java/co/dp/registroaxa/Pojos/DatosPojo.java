package co.dp.registroaxa.Pojos;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class DatosPojo implements Parcelable {

    private int cedula;
    private String nombre;
    private String apellido;
    private String foto;
    private String fecha;
    private String ubicacion;
    private String matricula;
    private String ciudad;

    @Override
    public String toString() {
        return "DatosPojo{" +
                "cedula=" + cedula +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", foto=" + foto +
                ", fecha='" + fecha + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", matricula='" + matricula + '\'' +
                ", ciudad='" + ciudad + '\'' +
                '}';
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public DatosPojo(int cedula, String nombre, String apellido, String foto, String fecha, String ubicacion, String matricula, String ciudad) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.foto = foto;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.matricula = matricula;
        this.ciudad = ciudad;
    }

    protected DatosPojo(Parcel in) {
        cedula = in.readInt();
        nombre = in.readString();
        apellido = in.readString();
        foto = in.readParcelable(Uri.class.getClassLoader());
        fecha = in.readString();
        ubicacion = in.readString();
        matricula = in.readString();
        ciudad = in.readString();
    }

    public static final Creator<DatosPojo> CREATOR = new Creator<DatosPojo>() {
        @Override
        public DatosPojo createFromParcel(Parcel in) {
            return new DatosPojo(in);
        }

        @Override
        public DatosPojo[] newArray(int size) {
            return new DatosPojo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(cedula);
        parcel.writeString(nombre);
        parcel.writeString(apellido);
        parcel.writeString(foto);
        parcel.writeString(fecha);
        parcel.writeString(ubicacion);
        parcel.writeString(matricula);
        parcel.writeString(ciudad);
    }
}
