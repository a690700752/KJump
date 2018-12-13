package com.werfad;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.DumbAwareAction;
import org.jetbrains.annotations.NotNull;

public class Actions {
    private static class BaseAction extends DumbAwareAction {
        int mode;

        @Override
        public void update(@NotNull AnActionEvent e) {
            Editor editor = e.getData(CommonDataKeys.EDITOR);
            e.getPresentation().setEnabled(editor != null);
        }

        @Override
        public void actionPerformed(@NotNull AnActionEvent e) {
            Editor editor = e.getData(CommonDataKeys.EDITOR);
            if (editor != null) {
                JumpHandler.getInstance().start(editor, mode);
            }
        }
    }

    public static class KJumpAction extends BaseAction {
        {
            mode = JumpHandler.MODE_CHAR1;
        }
    }

    public static class Char2Action extends BaseAction {
        {
            mode = JumpHandler.MODE_CHAR2;
        }
    }

    public static class Word0Action extends BaseAction {
        {
            mode = JumpHandler.MODE_WORD0;
        }
    }

    public static class Word1Action extends BaseAction {
        {
            mode = JumpHandler.MODE_WORD1;
        }
    }

    public static class LineAction extends BaseAction {
        {
            mode = JumpHandler.MODE_LINE;
        }
    }
}
