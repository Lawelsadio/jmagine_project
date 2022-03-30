package jmagine

class Response400 {
    String message = "BAD REQUEST"

    Response400(String message){
        this.message = message
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
