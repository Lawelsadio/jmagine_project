package jmagine

class UrlMappings {

    static mappings = {



        "/qrcode/text"(controller: "qrcode", action:"text")
        "/my_account"(controller: "users", action: "my_account")
        "/my_account/edit"(controller: "users", action: "edit_me")

        "/users"(controller: "users", action: "list")
        "/users/add"(controller: "users", action: "add")
//        "/users/test"(controller: "users", action: "test")

        // s'inscrire
        "/users/signup"(controller: "users", action: "signup")

        "/users/do_add"(controller: "users", action: "do_add")
        "/users/$user_id/edit"(controller: "users", action: "edit")
        "/users/$user_id/do_edit"(controller: "users", action: "do_edit")
        "/users/$user_id/enable"(controller: "users", action: "enable")
        "/users/$user_id/disable"(controller: "users", action: "disable")
        "/users/$user_id/delete"(controller: "users", action: "delete")
        group "/parcours",{
            "/"(controller: "parcours", action: "list")
            "/add"(controller: "parcours", action: "add")
            "/do_add"(controller: "parcours", action: "do_add")

            //POIS

            "/$p_id/enable"(controller: "parcours", action: "enable")
            "/$p_id/disable"(controller: "parcours", action: "disable")

            "/$p_id/edit"(controller: "parcours", action: "edit")
            "/$p_id/delete"(controller: "parcours", action: "delete")
            "/$p_id/do_info_edit"(controller: "parcours", action: "do_info_edit")
            "/$p_id/pois"(controller: "pois", action: "list", )
            "/$p_id/pois/add"(controller: "pois", action: "add")
            "/$p_id/pois/do_add"(controller: "pois", action: "do_add")
            "/$p_id/pois/$poi_id/edit"(controller: "pois", action: "edit")
            "/$p_id/pois/$poi_id/do_edit"(controller: "pois", action: "do_edit")
            "/$p_id/pois/$poi_id/move_to/$index"(controller: "pois", action:"move")
            "/$p_id/pois/$poi_id/delete"(controller: "pois", action: "delete")



//        "/parcours/$p_id/comments" ( controller: "comments", action:"list" )

            "/$p_id/medias"(controller: "medias", action: "list")
            "/$p_id/medias/get_json_img_list"(controller: "medias", action: "get_json_img_list")
            "/$p_id/medias/upload"(controller: "medias", action: "upload")

            "/$p_id/moderators"(controller: "moderators", action: "list")
            "/$p_id/moderators/add"(controller: "moderators", action: "add")
            "/$p_id/moderators/do_add/$user_id"(controller: "moderators", action: "do_add")
            "/$p_id/moderators/do_remove/$user_id"(controller: "moderators", action: "do_remove")

            "/$p_id/sections"(controller: "sections", action: "list")
            "/$p_id/sections/do_add"(controller: "sections", action: "do_add")
            "/$p_id/sections/add"(controller: "sections", action: "add")
            "/$p_id/sections/$s_id/delete"(controller: "sections", action: "delete")
            "/$p_id/sections/$s_id/edit"(controller: "sections", action: "edit")
            "/$p_id/sections/$s_id/move_to/$index"(controller: "sections", action: "move")
            "/$p_id/sections/$s_id/do_info_edit"(controller: "sections", action: "do_info_edit")
        }
        group "/api",{
            "/parcours"(controller:"parcours", action:"api_get_all")
            "/parcours/$p_id/pois/$poi_id"(controller:"pois", action:"api_get")
            "/parcours/$p_id/full"(controller:"parcours", action:"api_get_full")
            "/parcours/$p_id"(controller:"parcours", action:"api_get")

            "/parcours/*-$p_id" {
                controller = "parcours"
                action = "api_get_from_title"
                constraints {
                    p_id(matches: /\d/)
                }
            }

        }




//        "/auth"(controller:"Login",action:"auth_fail")
        "/auth"(controller:"Login",action:"auth")

        "/"(controller: "parcours", action: "list")
        "500"(view:'/error')
        "404"(view:'/page_not_found')
        "403"(view:'/page_forbidden')
    }

}
