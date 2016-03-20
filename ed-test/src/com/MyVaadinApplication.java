package com;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

import java.util.ArrayList;

/**
 * Created by user on 19.03.16.
 */
public class MyVaadinApplication extends UI {

    private String[] arrBlSource = {"ш1т", "г1т", "ш2т", "г2т", "ш3т", "г3т", "ш4т", "г4т", "ш5т", "г5т", "ш6т", "г6т",
            "ш51", "г51", "ш61т", "г61т", "ш62т", "г62т", "ш63т", "г63т", "ш64т", "г64т",
            "ш65т", "г65т", "ш66т", "г66т", "ш67т", "г67т", "ш68т", "г68т", "ш69т", "г69т", "ш70т",
            "г70т", "ш75т", "г75т", "ш76т", "г76т", "ш77т", "г77т", "ш78т", "г78т", "ш79т",
            "г79т", "ш80т", "г80т", "ш81т", "г81т", "ш82т", "г82т", "п1", "п2", "п3"};
    private String[] arrBlSize = {"1", "1", "2", "2", "1", "1", "2", "2", "1", "1", "2", "2",
            "2", "2", "2", "2", "2", "2", "2", "2", "2", "2",
            "2", "2", "2", "2", "2", "2", "2", "2", "2", "2", "2",
            "2", "2", "2", "2", "2", "2", "2", "2", "2", "3",
            "3", "3", "3", "2", "2", "3", "3", "1", "2", "3"};

    private ArrayList<String> arrBlForCreate = new ArrayList<>();
    private String[] arrSize = {"1","2","3","4","5","6","7","9"};
    private Label l = new Label();

    @Override
    public void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        setContent(layout);
        Button b = new Button("ok");
        TextArea t = new TextArea();
        l = new Label();
        b.addClickListener(e->{
            if (check(t.getValue()).equals("Success")) Notification.show("Успех "+arrBlForCreate);
            else Notification.show(check(t.getValue()));
        });

        //creation
        /*blocks.removeAll();
        imgLayout.removeAll();
        view.setDefault(sSize,sVR,sKozh);
        for (int j=0;j<arrBlForCreate.size;j++)
            view.tryAddBlock(arrBlForCreate.get(j).getValue());*/

        layout.addComponents(t,b,l);
    }

    public String check (String s) {

        //String oldsVR=skpCase.getTypeVR();
        //String oldsKozh=skpCase.getKozhuh();
        //String oldsSize=skpCase.getSizeCase();

        String sVR="";
        String sKozh="";
        String sSize="1";
        int fSize=0;
        arrBlForCreate = new ArrayList<>();

        int i=0;
        String flag="";

        s=s.trim();
        s=s.toLowerCase();

        if(s.indexOf("вилка")!=0 && s.indexOf("розетка")!=0 && s.indexOf("скп410")!=0)
            return "Обозначение изделия должно начинаться с типа контакта (Вилка, Розетка) или непосредственно с типа соединителя СКП410";
        else if (s.indexOf("скп410")==0) i=6;
        else if (s.indexOf("вилка")==0){
            sVR = "Вилка";
            if (s.indexOf(" ")!=5) return "После обозначения типа контакта (Вилка) должен следовать знак пробела";
            else if (s.indexOf("скп410")>0 && s.indexOf("скп410")!=6 ) return "Тип соединителя СКП410 должен указываться через пробел после типа контакта (Вилка)";
            else if (s.indexOf("скп410")==6) i=12;
            else return "После типа контакта (Вилка) должен указываться тип соединителя СКП410";
        }else if (s.indexOf("розетка")==0){
            sVR = "Розетка";
            if (s.indexOf(" ")!=7) return "После обозначения типа контакта (Розетка) должен следовать знак пробела";
            else if (s.indexOf("скп410")>0 && s.indexOf("скп410")!=8 ) return "Тип соединителя СКП410 должен указываться через пробел после типа контакта (Розетка)";
            else if (s.indexOf("скп410")==8) i=14;
            else return "После типа контакта (Розетка) должен указываться тип соединителя СКП410";
        }

        if (s.indexOf("-")!=i) return "После типа соединителя СКП410 должен следовать знак тире";
        else i++;

        //l.setValue(sSize +"  "+ String.valueOf(i) + String.valueOf(s.indexOf("11")) +"  "+arrSize[0] );


        if(i+1<=s.length()) {
            flag="";
            for (String j : arrSize) if (s.substring(i,i+1).equals(j)) flag = j;
            if (flag.equals("")) return "После знака тире должен указываться размер корпуса";
            else {
                i++;
                sSize = flag;
                if (s.indexOf("11") == i - 1) {
                    i++;
                    sSize = "11";
                }
            }
        } else return "После знака тире должен указываться размер корпуса";


        if(i+1<=s.length()) {
            if (s.substring(i, i + 1).equals("в") && (sVR.equals("") || sVR.equals("Вилка"))) {
                i++;
                if (sVR.equals("")) sVR = "Вилка";

            } else if (s.substring(i, i + 1).equals("в") && sVR.equals("Розетка"))
                return "Несоответствие типа контакта в названии изделия и в его обозначении";
            else if (s.substring(i, i + 1).equals("р") && (sVR.equals("") || sVR.equals("Розетка"))) {
                i++;
                if (sVR.equals("")) sVR = "Розетка";
            } else if (s.substring(i, i + 1).equals("р") && sVR.equals("Вилка"))
                return "Несоответствие типа контакта в названии изделия и в его обозначении";
            else return "После типоразмера корпуса должен указываться тип контакта ('В' - вилка, 'Р' - розетка)";
        } else return "После типоразмера корпуса должен указываться тип контакта ('В' - вилка, 'Р' - розетка)";

        //l.setValue(l.getValue() + "\n" + sVR +"  "+ String.valueOf(i));

        if (s.indexOf("п1")!=i) return "После типа контакта должен указываться способ монтажа ('П1' - пайка объёмная)";
        else i+=2;

        if (s.indexOf("2")!=i) return "После способа монтажа должно указываться покрытие контактов ('2' - серебро)";
        else i+=1;

        if (s.indexOf(" (")==i) i+=2;
        else if (s.indexOf("(")==i) i+=1;
        else return "После покрытия контактов должна указываться через пробел открывающаяся скобка, в которой перечислены набранные колодки";

        //скобки не должны быть пустыми
        if (s.indexOf(")")>0 && s.indexOf(")")>i+1) i=s.indexOf(")")+1;
        else if (s.indexOf(")")<=i+1) return "В скобках должны быть перечислены через запятую набранные колодки";
        else return "Список набранных колодок должен завершаться закрывающейся скобкой";

        if (i==s.length()) sKozh="Блочный без кожуха";
        else{
            switch(s.substring(i,s.length())){
                case "б": sKozh="Блочный без кожуха";
                    break;
                case " б": sKozh="Блочный без кожуха";
                    break;
                case "п": if (sVR=="Вилка") sKozh="Приборный без кожуха";
                else return "Несоответствие типа контакта и кожуха";
                    break;
                case " п": if (sVR=="Вилка") sKozh="Приборный без кожуха";
                else return "Несоответствие типа контакта и кожуха";
                    break;
                case "пкп": if (sVR=="Вилка")  sKozh="Приборно-кабельный с кожухом прямым";
                else return "Несоответствие типа контакта и кожуха";
                    break;
                case " пкп": if (sVR=="Вилка") sKozh="Приборно-кабельный с кожухом прямым";
                else return "Несоответствие типа контакта и кожуха";
                    break;
                case "пку": if (sVR=="Вилка") sKozh="Приборно-кабельный с кожухом угловым";
                else return "Несоответствие типа контакта и кожуха";
                    break;
                case " пку": if (sVR=="Вилка") sKozh="Приборно-кабельный с кожухом угловым";
                else return "Несоответствие типа контакта и кожуха";
                    break;
                case "кп": if (sVR=="Розетка") sKozh="Кабельный с кожухом прямым";
                else return "Несоответствие типа контакта и кожуха";
                    break;
                case " кп": if (sVR=="Розетка") sKozh="Кабельный с кожухом прямым";
                else return "Несоответствие типа контакта и кожуха";
                    break;
                case "ку": if (sVR=="Розетка") sKozh="Кабельный с кожухом угловым";
                else return "Несоответствие типа контакта и кожуха";
                    break;
                case " ку": if (sVR=="Розетка") sKozh="Кабельный с кожухом угловым";
                else return "Несоответствие типа контакта и кожуха";
                    break;
                case "бкп": if (sVR=="Розетка") sKozh="Блочный с кожухом прямым";
                else return "Несоответствие типа контакта и кожуха";
                    break;
                case " бкп": if (sVR=="Розетка") sKozh="Блочный с кожухом прямым";
                else return "Несоответствие типа контакта и кожуха";
                    break;
                case "бку": if (sVR=="Розетка") sKozh="Блочный с кожухом угловым";
                else return "Несоответствие типа контакта и кожуха";
                    break;
                case " бку": if (sVR=="Розетка") sKozh="Блочный с кожухом угловым";
                else return "Несоответствие типа контакта и кожуха";
                    break;
                default: return "После закрывающейся скобки должна указываться аббревиатура конструктивной разновидности кожуха";
            }
        }

        //parseBlocks(String s)
        s = s.substring(s.indexOf("(")+1, s.indexOf(")"));
        s = s.trim();

        //l.setValue(l.getValue() + "\n" + s +"  "+ String.valueOf(i));

        while (s.length()>1){
            i=0;
            for (int j=0;j<arrBlSource.length;j++){
                if (s.indexOf(arrBlSource[j]) == i){
                    if (sVR.equals("Вилка") && s.substring(i,i+1).equals("ш")) return "Вилка не может содержать штырьевые колодки";
                    if (sVR.equals("Розетка") && s.substring(i,i+1).equals("г")) return "Розетка не может содержать гнездовые колодки";

                    i+=arrBlSource[j].length();
                    if (arrBlForCreate.size()>0 && arrBlForCreate.get(arrBlForCreate.size()-1)!=arrBlSource[j]) {
                        arrBlForCreate.add(arrBlSource[j]);
                        fSize+=Integer.parseInt(arrBlSize[j]);
                    }
                    else if (arrBlForCreate.size()==0) {
                        arrBlForCreate.add(arrBlSource[j]);
                        fSize+=Integer.parseInt(arrBlSize[j]);
                    }
                    else   return "Подряд идущие одинаковые типовые колодки должны указываться одним условным обозначением\nВ этом случае перед условным обозначением типовых колодок указывается их количество";
                    break;
                } else if (s.indexOf(arrBlSource[j]) == i+1){
                    for (int k=2;k<10;k++){
                        if(s.indexOf(String.valueOf(k))==i) {
                            i+=arrBlSource[j].length()+1;
                            if (arrBlForCreate.size()>0 && arrBlForCreate.get(arrBlForCreate.size()-1)!=arrBlSource[j])
                                for (int m=1;m<=k;m++){
                                    arrBlForCreate.add(arrBlSource[j]);
                                    fSize+=Integer.parseInt(arrBlSize[j]);
                                }
                            else if (arrBlForCreate.size()==0)
                                for (int m=1;m<=k;m++){
                                    arrBlForCreate.add(arrBlSource[j]);
                                    fSize+=Integer.parseInt(arrBlSize[j]);
                            }
                            else return "Подряд идущие одинаковые типовые колодки должны указываться одним условным обозначением\nВ этом случае перед условным обозначением типовых колодок указывается их количество";
                            break;
                        }
                    }
                    if (i>0) break;
                } else if (s.indexOf(arrBlSource[j]) == i+2){
                    for (int k=1;k<5;k++){
                        if(s.indexOf("1")==i && s.indexOf(String.valueOf(k))==i+1) {
                            i+=arrBlSource[j].length()+2;
                            if (arrBlForCreate.size()>0 && arrBlForCreate.get(arrBlForCreate.size()-1)!=arrBlSource[j])
                                for (int m=1;m<=k+10;m++){
                                    arrBlForCreate.add(arrBlSource[j]);
                                    fSize+=Integer.parseInt(arrBlSize[j]);
                                }
                            else if (arrBlForCreate.size()==0)
                                for (int m=1;m<=k+10;m++){
                                    arrBlForCreate.add(arrBlSource[j]);
                                    fSize+=Integer.parseInt(arrBlSize[j]);
                            }
                            else return "Подряд идущие одинаковые типовые колодки должны указываться одним условным обозначением\nВ этом случае перед условным обозначением типовых колодок указывается их количество";
                            break;
                        }
                    }
                    if (i>0) break;
                }
            }
            if (i==0) return "В скобках должны указываться условные обозначения типовых колодок, разделенные запятой\nВ случае подряд идущих одинаковых типовых колодок перед их условным обозначением указывается количество";


            if (i+2<s.length()){
                if(s.indexOf(", ")==i) i+=2;
                else if(s.indexOf(",")==i) i+=1;
                else return "Условные обозначения типовых колодок должны быть разделены запятой";
                if (i+2<=s.length()) {
                    s = s.substring(i, s.length());
                    s = s.trim();
                } else return "Ошибка обозначения типовых колодок в скобках";

            }
            else if (s.length()!=i) return "Ошибка обозначения типовых колодок в скобках";
            else if(fSize!=Integer.parseInt(sSize)+3) return "Количество типовых колодок не соответствует типоразмеру корпуса";
            else break;

        }

        //проверка правил
        for (int j=0;j<arrBlForCreate.size();j++){
            //если высоковольтные колодки
            if (arrBlForCreate.get(j).substring(1,arrBlForCreate.get(j).length()).equals("51")){
                if (j>0 && (arrBlForCreate.get(j-1).substring(1,arrBlForCreate.get(j-1).length()).equals("5т") || arrBlForCreate.get(j-1).substring(1,arrBlForCreate.get(j-1).length()).equals("6т")))
                    return "При использовании в одном соединителе высоковольтных типовых колодок совместно с сильноточными низкочастотными типовыми колодками (с контактами диаметром 2,5 мм и 3,5 мм) они должны быть разделены между собой слаботочными низкочастотными типовыми колодками или колодками без контактов (пустыми)";
                if (j<arrBlForCreate.size()-1 && (arrBlForCreate.get(j+1).substring(1,arrBlForCreate.get(j+1).length()).equals("5т") || arrBlForCreate.get(j+1).substring(1,arrBlForCreate.get(j+1).length()).equals("6т")))
                    return "При использовании в одном соединителе высоковольтных типовых колодок совместно с сильноточными низкочастотными типовыми колодками (с контактами диаметром 2,5 мм и 3,5 мм) они должны быть разделены между собой слаботочными низкочастотными типовыми колодками или колодками без контактов (пустыми)";
                //патрубки
            }
            //в угловых кожухах только низкочастотные
            if (sKozh.equals("Приборно-кабельный с кожухом угловым") || sKozh.equals("Кабельный с кожухом угловым") || sKozh.equals("Блочный с кожухом угловым")){
                if (arrBlForCreate.get(j).substring(1,arrBlForCreate.get(j).length())!="1Т"
                        && arrBlForCreate.get(j).substring(1,arrBlForCreate.get(j).length())!="2Т"
                        && arrBlForCreate.get(j).substring(1,arrBlForCreate.get(j).length())!="3Т"
                        && arrBlForCreate.get(j).substring(1,arrBlForCreate.get(j).length())!="4Т"
                        && arrBlForCreate.get(j).substring(1,arrBlForCreate.get(j).length())!="5Т"
                        && arrBlForCreate.get(j).substring(1,arrBlForCreate.get(j).length())!="6Т"
                        && arrBlForCreate.get(j).substring(1,arrBlForCreate.get(j).length())!="1"
                        && arrBlForCreate.get(j).substring(1,arrBlForCreate.get(j).length())!="2"
                        && arrBlForCreate.get(j).substring(1,arrBlForCreate.get(j).length())!="3"
                        ) return "В корпусах с угловыми кожухами допускается устанавливать только низкочастотные типовые колодки";
            }
        }


        return "Success";
    }
}
