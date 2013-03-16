/*
 *  The scanner definition for COOL.
 */

import java_cup.runtime.Symbol;

%%

%{

/*  Stuff enclosed in %{ %} is copied verbatim to the lexer class
 *  definition, all the extra variables/functions you want to use in the
 *  lexer actions should go here.  Don't remove or modify anything that
 *  was there initially.  */

    // Max size of string constants
    static int MAX_STR_CONST = 1025;

    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();

    private int curr_lineno = 1;
    int get_curr_lineno() {
	return curr_lineno;
    }

    private int comment_level = 0;

    private AbstractSymbol filename;

    void set_filename(String fname) {
	filename = AbstractTable.stringtable.addString(fname);
    }

    AbstractSymbol curr_filename() {
	return filename;
    }

    private class LexerException extends RuntimeException {
        public LexerException(String msg) {
            super(msg);
        }
    }

    private String unescapeString(String s) {
        StringBuilder sb = new StringBuilder();
        int status = 0; // 0 is normal, // 1 is "\\"
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (status == 0) {
                if (c == '\\') {
                    status = 1;
                } else if (c == '\n') {
                    // Strings should not contain \n until following a \\
                    curr_lineno++;
                    throw new LexerException("Unterminated string constant");
                } else if (c == 0) {
                    // Strings should not contain NULL
                    throw new LexerException("String contains null character");
                } else {
                    sb.append(c);
                }
            } else {
                switch (c) {
                    case 'b': sb.append('\b'); break;
                    case 't': sb.append('\t'); break;
                    case 'n': sb.append('\n'); curr_lineno++; break;
                    case 'f': sb.append('\f'); break;
                    case 0: throw new LexerException("String contains null character");
                    default: sb.append(c);
                }
                status = 0;
            }
        }
        if (status == 1) {
            throw new LexerException("Unterminated string constant");
        }
        return sb.toString();
    }
%}

%init{

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */

    // empty for now
%init}

%eofval{

/*  Stuff enclosed in %eofval{ %eofval} specifies java code that is
 *  executed when end-of-file is reached.  If you use multiple lexical
 *  states and want to do something special if an EOF is encountered in
 *  one of those states, place your code in the switch statement.
 *  Ultimately, you should return the EOF symbol, or your lexer won't
 *  work.  */

    switch(yy_lexical_state) {
    case YYINITIAL:
	/* nothing special to do in the initial state */
	break;
    case COMMENT:
       return new Symbol(TokenConstants.EOF);
       // don't work
       // return new Symbol(TokenConstants.ERROR, "EOF in comment");
    }
    return new Symbol(TokenConstants.EOF);
%eofval}

%class CoolLexer
%cup
%ignorecase

ALPHA=[A-Za-z]
DIGIT=[0-9]
TYPEID=[A-Z]({ALPHA}|{DIGIT}|_)*
OBJECTID=[a-z]({ALPHA}|{DIGIT}|_)*
STRING_TEXT=(\\\"|\\\n|[^\n\"]|\\[\r\n \t]+)*
WHITESPACE=[\ \t\f\x0b]
NEWLINE=[\r\n]

%state COMMENT

%%


<YYINITIAL>"class"		{ return new Symbol(TokenConstants.CLASS); }
<YYINITIAL>"else"		{ return new Symbol(TokenConstants.ELSE); }
<YYINITIAL>"fi"		{ return new Symbol(TokenConstants.FI); }
<YYINITIAL>"if"		{ return new Symbol(TokenConstants.IF); }
<YYINITIAL>"in"		{ return new Symbol(TokenConstants.IN); }
<YYINITIAL>"inherits"		{ return new Symbol(TokenConstants.INHERITS); }
<YYINITIAL>"let"		{ return new Symbol(TokenConstants.LET); }
<YYINITIAL>"loop"		{ return new Symbol(TokenConstants.LOOP); }
<YYINITIAL>"pool"		{ return new Symbol(TokenConstants.POOL); }
<YYINITIAL>"then"		{ return new Symbol(TokenConstants.THEN); }
<YYINITIAL>"while"		{ return new Symbol(TokenConstants.WHILE); }
<YYINITIAL>"case"		{ return new Symbol(TokenConstants.CASE); }
<YYINITIAL>"esac"		{ return new Symbol(TokenConstants.ESAC); }
<YYINITIAL>"of"		{ return new Symbol(TokenConstants.OF); }
<YYINITIAL>"new"		{ return new Symbol(TokenConstants.NEW); }
<YYINITIAL>"not"		{ return new Symbol(TokenConstants.NOT); }
<YYINITIAL>"isvoid"		{ return new Symbol(TokenConstants.ISVOID); }

<YYINITIAL>"=>"			{ /* Sample lexical rule for "=>" arrow.
                                     Further lexical rules should be defined
                                     here, after the last %% separator */
                                  return new Symbol(TokenConstants.DARROW); }
<YYINITIAL>"<="		{ return new Symbol(TokenConstants.LE); }
<YYINITIAL>"<-"		{ return new Symbol(TokenConstants.ASSIGN); }
<YYINITIAL>"+"		{ return new Symbol(TokenConstants.PLUS); }
<YYINITIAL>"/"		{ return new Symbol(TokenConstants.DIV); }
<YYINITIAL>"-"		{ return new Symbol(TokenConstants.MINUS); }
<YYINITIAL>"*"		{ return new Symbol(TokenConstants.MULT); }
<YYINITIAL>"="		{ return new Symbol(TokenConstants.EQ); }
<YYINITIAL>"<"		{ return new Symbol(TokenConstants.LT); }
<YYINITIAL>"."		{ return new Symbol(TokenConstants.DOT); }
<YYINITIAL>"~"		{ return new Symbol(TokenConstants.NEG); }
<YYINITIAL>","		{ return new Symbol(TokenConstants.COMMA); }
<YYINITIAL>";"		{ return new Symbol(TokenConstants.SEMI); }
<YYINITIAL>":"		{ return new Symbol(TokenConstants.COLON); }
<YYINITIAL>"("		{ return new Symbol(TokenConstants.LPAREN); }
<YYINITIAL>")"		{ return new Symbol(TokenConstants.RPAREN); }
<YYINITIAL>"@"		{ return new Symbol(TokenConstants.AT); }
<YYINITIAL>"{"		{ return new Symbol(TokenConstants.LBRACE); }
<YYINITIAL>"}"		{ return new Symbol(TokenConstants.RBRACE); }

<YYINITIAL>{WHITESPACE}+ {}
<YYINITIAL,COMMENT>{NEWLINE} {curr_lineno++;}

<YYINITIAL>{OBJECTID}        { 
    String text = yytext();
    char l = text.charAt(0);
    if (l >= 'a' && l <= 'z') {
        if (text.toLowerCase().equals("true")) {
            return new Symbol(TokenConstants.BOOL_CONST, true);
        } else if (text.toLowerCase().equals("false")) {
            return new Symbol(TokenConstants.BOOL_CONST, false);
        }
        return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext()));
    } else {
        return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext()));
    }
}
<YYINITIAL>\"{STRING_TEXT}\"?    {
    try {
        String input = yytext();
        if (input.length() == 1) {
            return new Symbol(TokenConstants.ERROR, "Unterminated string constant");
        }
        char endChar = input.charAt(input.length() - 1);
        if (endChar != '"') {
            return new Symbol(TokenConstants.ERROR, "Unterminated string constant");
        }
        String unescapedString = unescapeString(input.substring(1, input.length() - 1));
        if (unescapedString == null) {
           return new Symbol(TokenConstants.ERROR, "");
        }
        if (unescapedString.length() >= MAX_STR_CONST) {
           return new Symbol(TokenConstants.ERROR, "String constant too long");
        }
        return new Symbol(TokenConstants.STR_CONST, AbstractTable.stringtable.addString(unescapedString));
    } catch (LexerException e) {
           return new Symbol(TokenConstants.ERROR, e.toString());
    }
}

<YYINITIAL>[0-9]+    {return new Symbol(TokenConstants.INT_CONST, AbstractTable.stringtable.addString(yytext()));}

<YYINITIAL>--.*    {}
<YYINITIAL>\(\*   { yybegin(COMMENT); comment_level++; }
<COMMENT>\(\*   { comment_level++; }
<YYINITIAL,COMMENT>\*\)     { 
    comment_level--;
    if (comment_level == 0) {
        yybegin(YYINITIAL); 
    } else if (comment_level < 0) {
        comment_level = 0;
        return new Symbol(TokenConstants.ERROR, "Unmatched *)");
    }
}
<COMMENT>.     { 
        // return new Symbol(TokenConstants.ERROR, "EOF in comment");
}

<YYINITIAL> . {
           return new Symbol(TokenConstants.ERROR, yytext());
}

<YYINITIAL>
.                               { /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  System.err.println("LEXER BUG - UNMATCHED: " + yytext()); }
