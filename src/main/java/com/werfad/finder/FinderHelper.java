package com.werfad.finder;

import com.werfad.MarksCanvas;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FinderHelper {

    /**
     * @return Return the new marks whose start character is removed.
     */
    @NotNull
    public static List<MarksCanvas.Mark> matchInputAndCreateMarks(char c, @NotNull List<MarksCanvas.Mark> marks) {
        ArrayList<MarksCanvas.Mark> newMarks = new ArrayList<>();

        for (MarksCanvas.Mark mark : marks) {
            if (mark.getKeyTag().charAt(0) == c) {
                if (mark.getKeyTag().length() == 1) {
                    newMarks.add(mark);
                    return newMarks;
                } else {
                    mark.setKeyTag( mark.getKeyTag().substring(1));
                    newMarks.add(mark);
                }
            }
        }

        return newMarks;
    }
}
