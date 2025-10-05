package chess;

public final class Board {
    public static final int FILES = 8;
    public static final int RANKS = 8;

    private Board() { }
        public static boolean inBounds (int fileIndex, int rankIndex)
        {
            return fileIndex >= 0 && fileIndex < FILES && rankIndex >= 0 && rankIndex < RANKS;
        }
        public static int fileIndex(char fileChar)
        {
            char c = Character.toLowerCase(fileChar);
            {
                if (c < 'a' || c > 'h')
                {
                    throw new IllegalArgumentException("File must be a..h: " + fileChar);
                }
                return c - 'a'; 
            }
            
        }

            public static int rankIndex(char rankChar)
            {
                if (rankChar <'1'  || rankChar > '8')
                {
                    throw new IllegalArgumentException("Rank must be 1.." + rankChar);
                }
                return rankChar - '1';
            }

            public static char fileChar(int fileIndex)
            {
                if (fileIndex < 0 || fileIndex >= FILES)
                {
                    throw new IllegalArgumentException("file out range: " + fileIndex);
                }
                return (char) ('a' + fileIndex);
            }

            public static int rankNumber (int rankIndex) 
            {
                if (rankIndex < 0 || rankIndex >= RANKS)
                {
                    throw new IllegalArgumentException("rank out of range: " + rankIndex);

                }
                return rankIndex + 1;
            }

            public static String toAlgebraic (int fileIndex, int rankIndex)
            {
                return "" + fileChar(fileIndex) + rankNumber(rankIndex);
            }
        }
    
