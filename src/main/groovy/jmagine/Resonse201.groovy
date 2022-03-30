package jmagine

class Response201 {
    boolean CREATED

    Response201(boolean CREATED){

        this.CREATED = CREATED
    }
}

/*
Methods – GET

200 (OK) Request was successfull
404 (NOT FOUND)
400 (BAD REQUEST)

Methods – POST
201 (CREATED)  `
Request was successfull
Resource was created

204 (NO CONTENT)
Request was successfull
No data was returned
404 (NOT FOUND)
400 (BAD REQUEST)

Methods – PUT

200 (OK) Request was successfull
201 (CREATED)
204 (NO CONTENT)
Request was successfull
No data was returned
404 (NOT FOUND)
400 (BAD REQUEST)

Methods – DELETE

200 (OK) Request was successfull
204 (NO CONTENT)
Request was successfull
No data was returned
404 (NOT FOUND)
400 (BAD REQUEST)


 */
