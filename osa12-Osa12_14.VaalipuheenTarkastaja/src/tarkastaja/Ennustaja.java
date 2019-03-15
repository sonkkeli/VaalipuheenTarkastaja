package tarkastaja;

import java.util.HashMap;

public class Ennustaja {
    HashMap<String, Integer> valitut;
    HashMap<String, Integer> hylatyt;
    int lauseitaYhteensa;
    int valittujaLauseita;
    int hylattyjaLauseita;

    public Ennustaja() {
        this.valitut = new HashMap<>();
        this.hylatyt = new HashMap<>();
    }

    public void lisaa(boolean valitaan, String lause) {
        lauseitaYhteensa++;
        
        if (valitaan){
            valittujaLauseita++;            
        } else if (!valitaan){
            hylattyjaLauseita++;
        }
        
        String[] sanat = lause.trim().split("\\s+");
        if (valitaan){            
            for(String sana : sanat){
                this.valitut.putIfAbsent(sana, 0);
                int ennen = this.valitut.get(sana);
                this.valitut.replace(sana, ennen+1);
            }
        } else {
            for(String sana : sanat){
                this.hylatyt.putIfAbsent(sana, 0);
                int ennen = this.hylatyt.get(sana);
                this.hylatyt.replace(sana, ennen+1);
            }
        }
    }

    public int valitaanSanojenLukumaariinPerustuen(String lause) {
        int esiintyminenValituissa = 0;
        int esiintyminenHylatyissa = 0;
        
        String[] sanat = lause.trim().split("\\s+");
        for (String sana : sanat){
            if (valitut.containsKey(sana)){
                if (hylatyt.containsKey(sana)){
                    if (valitut.get(sana) > hylatyt.get(sana)) {
                        esiintyminenValituissa++;
                    }
                } else {
                    esiintyminenValituissa++;
                }                
            }
            
            if (hylatyt.containsKey(sana)){
                if (valitut.containsKey(sana)){
                    if (hylatyt.get(sana) > valitut.get(sana)) {
                        esiintyminenHylatyissa++;
                    }
                } else {
                    esiintyminenHylatyissa++;
                }
            }
        }
        
        if (esiintyminenValituissa > esiintyminenHylatyissa){
            return 1;
        } else if (esiintyminenHylatyissa > esiintyminenValituissa){
            return -1;
        } else {
            return 0;
        }        
    }

    public int valitaanMystisellaEnnustajalla(String lause) {
        double valitaan = (double) this.valittujaLauseita / (double) this.lauseitaYhteensa;
        double hylataan = (double) this.hylattyjaLauseita / (double) this.lauseitaYhteensa;        
        
        String[] sanat = lause.trim().split("\\s+");
        for (String sana : sanat){
            if (valitut.containsKey(sana) && hylatyt.containsKey(sana)){
                if ( (this.valitut.get(sana) + this.hylatyt.get(sana)) >= 5 ){
                    valitaan = valitaan * (double) ((double) valitut.get(sana) / 
                            ((double) valitut.get(sana) + (double) hylatyt.get(sana)) );
                    
                    hylataan = hylataan * (double) ((double) hylatyt.get(sana) / 
                            ((double) valitut.get(sana) + (double) hylatyt.get(sana)) );                    
                }
            }
        }
        
        if (valitaan > hylataan){
            return 1;
        }        
        if (hylataan > valitaan){
            return -1;
        }        
        return 0;
        
    }
}
