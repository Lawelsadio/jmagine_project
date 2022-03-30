package jmagine

/**
 * Created by Gav on 01/04/2015.
 */
enum OwnerType
{
    BACKEND ( 0 ),
    MOBILE ( 1 )

    private final int code

    OwnerType( int code )
    {
        this.code = code
    }
}