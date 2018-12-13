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

public class LineFinder implements Finder {
    private static final Pattern pattern = Pattern.compile("(?m)^");

    @Nullable
    @Override
    public List<MarksCanvas.Mark> start(@NotNull Editor e, @NotNull String s, @NotNull TextRange visibleRange) {
        Matcher m = pattern.matcher(s);

        List<Integer> offsets = new ArrayList<>();
        while (m.find()) {
            offsets.add(m.start() + visibleRange.getStartOffset());
        }
        offsets.sort((o1, o2) -> {
            int cOffset = e.getCaretModel().getOffset();
            return Math.abs(o1 - cOffset) - Math.abs(o2 - cOffset);
        });

        List<String> tags = KeyTagsGenerator.createTagsTree(offsets.size(), UserConfig.getDataBean().getCharacters());

        List<MarksCanvas.Mark> res = new ArrayList<>();

        for (int i = 0; i < offsets.size(); i++) {
            res.add(new MarksCanvas.Mark(tags.get(i), offsets.get(i)));
        }

        return res;
    }

    @Nullable
    @Override
    public List<MarksCanvas.Mark> input(@NotNull Editor e, char c, @NotNull List<MarksCanvas.Mark> lastMarks) {
        return FinderHelper.matchInputAndCreateMarks(c, lastMarks);
    }
}
