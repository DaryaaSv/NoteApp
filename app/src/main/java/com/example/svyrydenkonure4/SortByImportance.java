package com.example.svyrydenkonure4;

import android.content.Context;
import android.graphics.ColorSpace;

import java.util.Collections;
import java.util.Comparator;

    public class SortByImportance implements Comparator<Note> {

        @Override
        public int compare(Note o1, Note o2) {
            String p1 = o1.getImportance();
            String p2 = o2.getImportance();
            String imp1 = "so important";
            String imp2 = "quite important";
            String imp3 = "unimportant";

            String impU1 = "дуже важливо";
            String impU2 = "доволі важливо";
            String impU3 = "неважливо";

            if (p1.equals(p2)) {
                return 0;
            }
            if ((p1.equals(imp1) && (p2.equals(imp2) || p2.equals(imp3))) || (p1.equals(impU1) &&
                    (p2.equals(impU2) || p2.equals(impU3)))) {
                return -1;
            }
            if ((p1.equals(imp2) && p2.equals(imp3)) || (p1.equals(impU2) && p2.equals(impU3))) {
                return -1;
            }
            return 1;
        }
    }