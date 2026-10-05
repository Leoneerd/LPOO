package lpoo.math;

public class Vector3{
    //Atributos públicos da classe Vector_3
    int esc_x;
    int esc_y;
    int esc_z;

    //Construtores da classe Vector_3
    Vector3(){}

    Vector3(int x, int y, int z){
        this.esc_x = x;
        this.esc_y = y;
        this.esc_z = z;
    }

    //Métodos públicos da classe Vector_3
    Vector3 sum(final Vector3 v){        //Esse método que faz a soma de dois vetores,
        return new Vector3(esc_x+v.esc_x, esc_y+v.esc_y, esc_z+v.esc_z);   //retorna um NOVO OBJETO DA CLASSE Vector_3,
    }                                      //que tem como valores, A SOMA DOS ESCALARES

    Vector3 sub(final Vector3 v){
        return new Vector3(esc_x-v.esc_x, esc_y-v.esc_y, esc_z-v.esc_z);
    }

    int prod_int(final Vector3 v){     //Calcula o produto interno entre dois vetores (Resulta em escalar)
        //ex.: (1, 2, 3).(4, 5, 6) = (1*4+2*5+3*6) = 32
        return (esc_x*v.esc_x) + (esc_y*v.esc_y) + (esc_z*v.esc_z);
    }

    Vector3 prod_vet(final Vector3 v){
        //Para fazer o cálculo do produto vetorial (v1 × v2), é necessário 
        //calcular o determinante da matriz quadrada formada por:
        // | i  j  k |
        // | x  y  z |
        // | a  b  c |
        //onde v1 = (x, y, z) e v2 = (a, b, c)
        return new Vector3(
            (esc_y*v.esc_z - v.esc_y*esc_z),
            (esc_z*v.esc_x - v.esc_z*esc_x),
            (esc_x*v.esc_y - v.esc_x*esc_y)
        );
    }

    double mod(){       //Calcula o tamanho do vetor -> Módulo
        //v = (1, 2, 3) -> |v| = √1²+2²+3² [int]
        double vvx = Math.pow(esc_x, 2);
        double vvy = Math.pow(esc_y, 2);
        double vvz = Math.pow(esc_z, 2);

        return Math.sqrt(vvx + vvy + vvz);
    }

    void print(String label){
        System.out.printf("%s: (%d, %d, %d)\n", label, esc_x, esc_y, esc_z);
    }
}