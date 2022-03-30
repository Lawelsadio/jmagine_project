package jmagine

/**
 * Created by Gav on 01/04/2015.
 */
enum FileType
{
    IMG ( 0 ),
    VIDEO ( 1 ),
    SOUND ( 2 ),
    IMG_CKEDITOR( 3 )

    private final int code

    FileType ( int code )
    {
        this.code = code
    }
}