package com.example.android.notebook.database;

/**
 *
 */

public class NoteDbSchema {

    public static final class NoteTable{

        public static final String NAME = "notes";

        public static final class Cols{
            public static final String ID = "id";
            public static final String TITLE = "title";
            public static final String COLOR = "color";
            public static final String CONTENT = "content";
            public static final String MODIFYTIME = "modifytime";

        }

    }
}
