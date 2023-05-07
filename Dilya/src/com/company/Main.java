package com.company;

import java.io.*;
import java.util.*;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static List<String> names = new ArrayList<String>();
    static Random rand = new Random();

    public static void main(String[] args) throws IOException {
        File fl = new File("src/com/company/dataset.txt");
        BufferedReader bf = new BufferedReader(new FileReader(fl));
        HashMap<String, nextChar> bound = new HashMap<String, nextChar>();
        String st;
        while((st = bf.readLine()) != null){
            String lastElement = "^" + st.charAt(0);

            //declaring the class if its first time appearing
            if(!bound.containsKey(lastElement))
                bound.put(lastElement, new nextChar());

            //If the word is "macharael"
            //the boundary will look like this:
            //m->a, a->c, c->h, h->a, a->c,r, r->a, a->c,r,e, e->l
            for (int i = 1; i < st.length() - 1; i++) {

                //declaring the class if its first time appearing
                if(!bound.containsKey(lastElement))
                    bound.put(lastElement, new nextChar());

                //adding to set if boundary exists
                else
                    bound.get(lastElement).hs.add(st.substring(i, i + 2));

                lastElement = st.substring(i, i + 2);
            }
            //declaring the class if its first time appearing
            if(!bound.containsKey(lastElement))
                bound.put(lastElement, new nextChar());

            bound.get(lastElement).hs.add(st.charAt(st.length() - 1) + "$");
        }
        String command = "Start";
        while (!command.equals("EXIT")){
            System.out.println("To exit the programm, write word \"EXIT\" "
                            + "\n------------------------------------------"
                            + "\nTo generate new Name or Nick, write \"GENERATE\"");
            command = sc.nextLine();
            if(command.equals("GENERATE"))
                generateName(bound);
            else System.err.print("You wrote wrong command!"
                                    + "\nTry again: ");
        }
    }

    private static void generateName(HashMap<String, nextChar> bound) {
        String nameLen = "1";
//        while(!isNumeric(nameLen) && (Integer.parseInt(nameLen) < 3 || Integer.parseInt(nameLen) > 13)){
            System.out.print("How long length of the name should be(3-13): ");
            nameLen = sc.nextLine();
//        }
        int nameLenInt = Integer.parseInt(nameLen);
        System.out.print("From which character name must start? You can write as (\"nAtRog\"): ");
        String nameYouWant = sc.nextLine().toLowerCase();
        char firstLetter = '1';
        while(Character.isDigit(firstLetter)){
            firstLetter = selectAChar(nameYouWant);
        }
        StringBuilder name = new StringBuilder(String.valueOf(firstLetter));
        System.out.println();
        String str = getRandomElement(bound.get("^" + firstLetter).hs);
        int i = 1;
        if(str.charAt(1) != '$') name.append(str.charAt(1));
        else i--;
        for (; i < nameLenInt; i++) {
            if(bound.containsKey(str)){
                if(!bound.get(str).hs.isEmpty()){
                    str = getRandomElement(bound.get(str).hs);
                    if(str.charAt(1) != '$') name.append(str.charAt(1));
                    else i--;
                }else i--;
            }
            else i--;
        }
        names.add(name.toString());
        System.out.println("New name is: " + name.toString());
    }
    public static char selectAChar(String s) {
        return s.charAt(rand.nextInt(s.length()));
    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    static class nextChar{
        HashSet<String> hs = new HashSet<String>();
    }
    private static <E> E getRandomElement(Set<? extends E> set){
        Random random = new Random();
        int randomNumber = random.nextInt(set.size());
        Iterator<? extends E> iterator = set.iterator();
        int currentIndex = 0;
        E randomElement = null;
        while(iterator.hasNext()){
            randomElement = iterator.next();
            if(currentIndex == randomNumber)
                return randomElement;
            currentIndex++;
        }
        return randomElement;
    }
}
