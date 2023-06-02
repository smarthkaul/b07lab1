class Polynomial{
    double polyarr[];
    Polynomial(){
        polyarr = new double[]{0};
    }
    Polynomial(double arr[]){
        polyarr = arr;
    }
    Polynomial add(Polynomial p){
        int length = Math.max(p.polyarr.length, this.polyarr.length);
        double sum[] = new double[length];
        for(int i = 0; i < polyarr.length; i++){
            sum[i] = polyarr[i];
        }
        for(int i = 0; i < p.polyarr.length; i++){
            sum[i] += p.polyarr[i];
        }
        Polynomial ans = new Polynomial(sum);
        return ans;
    }
    double evaluate(double val){
        double sum = 0;
        for(int i = 0; i < polyarr.length; i++){
            sum += polyarr[i]*Math.pow(val,i);
        }
        return sum;
    }
    boolean hasRoot(double val){
        return evaluate(val)==0;
    }
}