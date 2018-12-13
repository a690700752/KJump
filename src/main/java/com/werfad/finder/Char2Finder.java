package com.werfad.finder;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.TextRange;
import com.werfad.KeyTagsGenerator;
import com.werfad.MarksCanvas;
import com.werfad.UserConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Char2Finder implements Finder {
    private static final int STATE_WAIT_SEARCH_CHAR1 = 0;
    private static final int STATE_WAIT_SEARCH_CHAR2 = 1;
    private static final int STATE_WAIT_KEY = 2;
    private int state = STATE_WAIT_SEARCH_CHAR1;
    private UserConfig.DataBean config = UserConfig.getDataBean();

    private String s;
    private TextRange visibleRange;

    private char firstChar = ' ';

    @Nullable
    @Override
    public List<MarksCanvas.Mark> start(@NotNull Editor e, @NotNull String s, @NotNull TextRange visibleRange) {
        this.s = s;
        this.visibleRange = visibleRange;
        state = STATE_WAIT_SEARCH_CHAR1;
        return null;
    }

    @Nullable
    @Override
    public List<MarksCanvas.Mark> input(@NotNull Editor e, char c, @NotNull List<MarksCanvas.Mark> lastMarks) {
        switch (state) {
            case STATE_WAIT_SEARCH_CHAR1:
                firstChar = c;
                state = STATE_WAIT_SEARCH_CHAR2;
                return null;
            case STATE_WAIT_SEARCH_CHAR2:
                boolean ignoreCase = config.isSmartcase() && Character.isLowerCase(firstChar) && Character.isLowerCase(c);
                Pattern pattern = Pattern.compile((ignoreCase ? "(?i)" : "") + firstChar + c);
                Matcher m = pattern.matcher(s);

                List<Integer> offsets = new ArrayList<>();
                while (m.find()) {
                    offsets.add(m.start() + visibleRange.getStartOffset());
                }

                offsets.sort((o1, o2) -> {
                    int caretOffset = e.getCaretModel().getOffset();
                    return Math.abs(o1 - caretOffset) - Math.abs(o2 - caretOffset);
                });

                List<String> tags = KeyTagsGenerator.createTagsTree(offsets.size(), config.getCharacters());
                state = STATE_WAIT_KEY;

                List<MarksCanvas.Mark> res = new ArrayList<>();
                for (int i = 0; i < offsets.size(); i++) {
                    res.add(new MarksCanvas.Mark(tags.get(i), offsets.get(i)));
                }
                return res;
            case STATE_WAIT_KEY:
                return FinderHelper.matchInputAndCreateMarks(c, lastMarks);
            default:
                throw new RuntimeException("Impossible.");
        }
    }
}
