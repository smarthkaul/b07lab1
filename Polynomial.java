import java.io.*;
class Polynomial{
    double polyarr[];
    int exp[];
    double coeff[];
    Polynomial(){
        coeff = new double[]{0};
        exp = new int[]{0};
    }
    Polynomial(double coeff[], int exp[]){
        this.coeff = coeff;
        this.exp = exp;
    }
    Polynomial(File f) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(f));
        String polyString = br.readLine();
        br.close();
        String[] polyTerms = polyString.split("(?=[-+])");
        double[] coeff = new double[polyTerms.length];
        int[] exp =  new int[polyTerms.length];
        for(int i = 0; i < polyTerms.length; i++){
            String polyTerm = polyTerms[i].trim();
            String parts[] = polyTerm.split("x",2);
            if(parts.length==1){
                coeff[i] = Double.parseDouble(parts[0]);
                exp[i] = 0;
            }
            else if(parts.length==2){
                coeff[i] = Double.parseDouble(parts[0]);
                exp[i] = Integer.parseInt(parts[1]);
            }
        }
        this.coeff = coeff;
        this.exp = exp;
    }
    void saveToFile(String fileName) throws IOException{
        String polyWrite = "";
        for(int i = 0; i < this.coeff.length; i++){
            if(i != 0){
                if(this.coeff[i]>0){
                    polyWrite = polyWrite + "+";
                }
            }
            polyWrite = polyWrite + this.coeff[i];
            if(this.exp[i] == 0){
                continue;
            }
            polyWrite = polyWrite + "x" + this.exp[i];
        }
        FileWriter writer = new FileWriter(fileName);
        writer.write(polyWrite);
        writer.close();
    }

    int findPos(int findExp, int[] exp){
        for (int i = 0; i < exp.length; i++) {
            if (findExp == exp[i])
                return i;
        }
        return -1;
    }

    Polynomial removeZero(double[] coeff, int[] exp){
        int nonZeroSize = 0;
        for(int i = 0; i < coeff.length; i++){
            if(coeff[i] != 0){
                nonZeroSize++;
            }
        }
        double simplifiedCoeff[] = new double[nonZeroSize];
        int simplifiedExp[] = new int[nonZeroSize];
        int index = 0;
        for(int i = 0; i < coeff.length; i++){
            if(coeff[i]!=0){
                simplifiedCoeff[index] = coeff[i];
                simplifiedExp[index] = exp[i];
                index++;
            }
        }
        return new Polynomial(simplifiedCoeff, simplifiedExp);
    }

    Polynomial multiply(Polynomial p){
        int size1 = this.coeff.length;
        int size2 = p.coeff.length;
        double[] multCoeff = new double[size2 * size1];
        int[] multExp = new int[size2 * size1];
        int index = 0;
        for(int i = 0; i < size1; i++){
            for(int j = 0; j < size2; j++){
                int pos = findPos((p.exp[j]+this.exp[i]),multExp);
                if(pos!=-1){
                    multCoeff[pos] += p.coeff[j]*this.coeff[i];
                }
                else{
                    multCoeff[index] = this.coeff[i]*p.coeff[j];
                    multExp[index] = this.exp[i] + p.exp[j];
                    index++;
                }
            }
        }
        return removeZero(multCoeff, multExp);
    }
    Polynomial add(Polynomial p){
        int size1 = this.coeff.length;
        int size2 = p.coeff.length;
        double[] addCoeff = new double[size2 + size1];
        int[] addExp = new int[size2 + size1];
        int index = 0;
        for(int i = 0; i < size1; i++){
            addCoeff[index] = this.coeff[i];
            addExp[index] = this.exp[i];
            index++;
        }
        for(int i = 0; i < size2; i++){
            int pos = findPos(p.exp[i], addExp);
            if(pos != -1){
                addCoeff[pos] += p.coeff[i];
            }
            else{
                addCoeff[index] = p.coeff[i];
                addExp[index] = p.exp[i];
                index++;
            }
        }
        return removeZero(addCoeff, addExp);
    }

    double evaluate(double val){
        double sum = 0;
        for(int i = 0; i < coeff.length; i++){
            sum += coeff[i]*Math.pow(val,exp[i]);
        }
        return sum;
    }
    
    boolean hasRoot(double val){
        return evaluate(val)==0;
    }
}