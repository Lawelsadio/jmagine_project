package jmagine

/**
 * Created by Gav on 01/04/2015.
 */
enum ModStatus
{
    UNMODERATED ( 0 ),
    VALIDATED ( 1 ),
    REJECTED ( 2 )

    private final int code

    ModStatus ( int code )
    {
        this.code = code
    }
}