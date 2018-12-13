package com.werfad.finder;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.TextRange;
import com.werfad.MarksCanvas;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Finder {

    /**
     * @return null - need more input to locate.
     * not null - can be locate some data, empty represent without any matches.
     */
    @Nullable
    List<MarksCanvas.Mark> start(@NotNull Editor e, @NotNull String s, @NotNull TextRange textRange);

    /**
     * @return same with {@link #start(Editor, String, TextRange)}
     */
    @Nullable
    List<MarksCanvas.Mark> input(@NotNull Editor e, char c, @NotNull List<MarksCanvas.Mark> lastMarks);
}
