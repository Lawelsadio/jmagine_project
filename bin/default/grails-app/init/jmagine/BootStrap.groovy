package jmagine

import grails.converters.XML
import org.springframework.mock.web.MockMultipartFile
import java.io.File;
import java.nio.file.Path;

import java.util.regex.Pattern

class BootStrap {

    PoiService poiService
    ParcoursService parcoursService
    UserService userService

    def grailsApplication

    File[] deleteOldfFiles(File root, String regex) {

        if (!root.isDirectory()) {
            throw new IllegalArgumentException(root + " is no directory.");
        }
        final Pattern p = Pattern.compile(regex);

        File[] listFiles = root.listFiles(new FileFilter() {
            @Override
            boolean accept(File file) {
                return p.matcher(file.getName()).matches();
            }
        });

        for (i in 0..<listFiles.size()) {
            listFiles[i].delete()

        }
    }


    def init = { servletContext ->




        if (!Role.findByAuthority('ROLE_OP')) {// si le role est different de ROLE_OP
            deleteOldfFiles(new File(grailsApplication.config.grails.assetspath.path), "(img)[0-9]*(.jpg)")

            XML.createNamedConfig('api_basic') { it.registerObjectMarshaller(new GenericMarshaller()) }

            User userOp, userOp2, userAdmin, userMod



            def roleOp = new Role(authority: 'ROLE_OP', level: 3).save(flush: false, failOnError: true)
            def roleAdmin = new Role(authority: 'ROLE_ADMIN', level: 2).save(flush: false, failOnError: true)
            def roleModerator = new Role(authority: 'ROLE_MOD', level: 1).save(flush: false, failOnError: true)

            try {
// ic on recupere un fichier et on cree 3 utilisateurs
                FileInputStream user_op_fis =
                        new FileInputStream(new File("src//main//webapp//images//default_avatar.png"));


                MockMultipartFile user_op_mmpf = new MockMultipartFile("test.txt", "test.png", "image/png", user_op_fis);

                userOp = userService
                        .createUser(imageType: 'upload',
                                username: "op",
                                password: "sadio123456789",
                                mail: "sadioop@gmail.com",
                                role: roleOp,
                                thumbnail: user_op_mmpf
                        )
                user_op_fis.close()

                userAdmin = userService
                        .createUser(username: "admin",
                                password: "4/A&N^K+6aRzyR@@",
                                mail: "sadioadmin@gmail.com",
                                role: roleAdmin,
                                thumbnail: user_op_mmpf
                        )

                userMod = userService
                        .createUser(username: "modo",
                                password: "emAP2Uy7=jUJ'TnX",
                                mail: "sadiomodo@gmail.com",
                                role: roleModerator,
                                thumbnail: user_op_mmpf
                        )



            }
            catch (e) {
                e.printStackTrace()
                println "Erreur durant le chargement du fichier"
            }


            try {

                FileInputStream inputStream1 =
                        new FileInputStream(new File("src//main//webapp//images//musee_arts_asiatiques.jpg"));
               // println(inputStream1);
                MockMultipartFile bg_musee_arts_asiatiques = new MockMultipartFile("test.txt", "test.png", "image/png", inputStream1);
              //  println(bg_musee_arts_asiatiques)
                inputStream1.close()


                FileInputStream inputStream2 =
                        new FileInputStream(new File("src//main//webapp//images//musee_beaux_arts.jpg"));
                MockMultipartFile bg_musee_beaux_arts =
                        new MockMultipartFile("test.txt", "test.png", "image/png", inputStream2);
                inputStream2.close()

                FileInputStream inputStream3 = new FileInputStream(new File("src//main//webapp//images//musee_marc_chagall.jpg"));
                MockMultipartFile bg_marc_chagall = new MockMultipartFile("test.txt", "test.png", "image/png", inputStream3);
                inputStream3.close()

                FileInputStream inputStream4 = new FileInputStream(new File("src//main//webapp//images//musee_mamac.jpg"));
                MockMultipartFile bg_mamac = new MockMultipartFile("test.txt", "test.png", "image/png", inputStream4);
                inputStream4.close()

                FileInputStream inputStream5 = new FileInputStream(new File("src//main//webapp//images//musee_matisse.jpg"));
                MockMultipartFile bg_matisse = new MockMultipartFile("test.txt", "test.png", "image/png", inputStream5);
                inputStream5.close()


                Parcours parcours = parcoursService
                        .createParcours(title: 'Musées Nice',
                                imageType: 'upload', background_picture: bg_musee_arts_asiatiques,
                                description: 'Lorem Lorem Lorem Lorem Lorem Lorem orem Lorem Lorem Lorem', author: userOp, isValidated: true)

                Parcours parcours2 = parcoursService
                        .createParcours(title: 'Musées Mamac',
                                imageType: 'upload', background_picture: bg_mamac,
                                description: 'Lorem Lorem Lorem Lorem Lorem ', author: userOp, isValidated: true)

                Parcours parcours3 = parcoursService
                        .createParcours(title: 'Musées Marc Chagall',
                                imageType: 'upload', background_picture: bg_marc_chagall,
                                description: 'Lorem Lorem Lorem Lorem Lorem Lorem Lorem ', author: userOp, isValidated: true)

                Parcours parcours4 = parcoursService
                        .createParcours(title: 'Musées Matisse',
                                imageType: 'upload', background_picture: bg_matisse,
                                description: 'Lorem Lorem Lorem Lorem Lorem Lorem Lorem ', author: userOp, isValidated: true)

                parcoursService.addComponent(title: 'Composant', imageType: 'upload',
                        'background_picture': bg_musee_beaux_arts, content: '<b>Huehuehue</b>', parcours: parcours)


                poiService.createPOI(imageType: 'upload', parcours: parcours, lat: '43.668065', lng: '7.216075', title: 'Musée des Arts Asiatiques', address: '405 Promenade des Anglais, 06200 Nice', author: userOp, background_picture: bg_musee_arts_asiatiques)


                poiService.createPOI(imageType: 'upload', parcours: parcours, lat: '43.694539', lng: '7.248851', title: 'Musée des Beaux Arts de Nice', address: '33 Avenue des Baumettes, 06000 Nice', author: userOp, background_picture: bg_musee_beaux_arts)
                poiService.createPOI(imageType: 'upload', parcours: parcours, lat: '43.709137', lng: '7.269403', title: 'Musée National Marc Chagall', address: '36 Avenue Docteur Ménard, 06000 Nice', author: userOp, background_picture: bg_marc_chagall)
                poiService.createPOI(imageType: 'upload', parcours: parcours, lat: '43.70146', lng: '7.278485', title: 'Musée d\'Art Moderne et d\'Art Contemporain', address: 'Place Yves Klein, 06364 Nice', author: userOp, background_picture: bg_mamac)
                poiService.createPOI(imageType: 'upload', parcours: parcours, lat: '43.719536', lng: '7.275381', title: 'Musée Matisse', address: '164 Avenue des Arènes de Cimiez, 06000 Nice', author: userOp, background_picture: bg_matisse)

                poiService.createPOI(imageType: 'upload', parcours: parcours2, lat: '43.709137', lng: '7.269403', title: 'Musée National Marc Chagall', address: '36 Avenue Docteur Ménard, 06000 Nice', author: userOp, background_picture: bg_marc_chagall)
                poiService.createPOI(imageType: 'upload', parcours: parcours2, lat: '43.70146', lng: '7.278485', title: 'Musée d\'Art Moderne et d\'Art Contemporain', address: 'Place Yves Klein, 06364 Nice', author: userOp, background_picture: bg_mamac)
                poiService.createPOI(imageType: 'upload', parcours: parcours2, lat: '43.719536', lng: '7.275381', title: 'Musée Matisse', address: '164 Avenue des Arènes de Cimiez, 06000 Nice', author: userOp, background_picture: bg_matisse)

                poiService.createPOI(imageType: 'upload', parcours: parcours3, lat: '43.668065', lng: '7.216075', title: 'Musée des Arts Asiatiques', address: '405 Promenade des Anglais, 06200 Nice', author: userOp, background_picture: bg_musee_arts_asiatiques)
                poiService.createPOI(imageType: 'upload', parcours: parcours3, lat: '43.694539', lng: '7.248851', title: 'Musée des Beaux Arts de Nice', address: '33 Avenue des Baumettes, 06000 Nice', author: userOp, background_picture: bg_musee_beaux_arts)
                poiService.createPOI(imageType: 'upload', parcours: parcours3, lat: '43.709137', lng: '7.269403', title: 'Musée National Marc Chagall', address: '36 Avenue Docteur Ménard, 06000 Nice', author: userOp, background_picture: bg_marc_chagall)
                poiService.createPOI(imageType: 'upload', parcours: parcours3, lat: '43.719536', lng: '7.275381', title: 'Musée Matisse', address: '164 Avenue des Arènes de Cimiez, 06000 Nice', author: userOp, background_picture: bg_matisse)

                poiService.createPOI(imageType: 'upload', parcours: parcours4, lat: '43.694539', lng: '7.248851', title: 'Musée des Beaux Arts de Nice', address: '33 Avenue des Baumettes, 06000 Nice', author: userOp, background_picture: bg_musee_beaux_arts)
                poiService.createPOI(imageType: 'upload', parcours: parcours4, lat: '43.709137', lng: '7.269403', title: 'Musée National Marc Chagall', address: '36 Avenue Docteur Ménard, 06000 Nice', author: userOp, background_picture: bg_marc_chagall)
                poiService.createPOI(imageType: 'upload', parcours: parcours4, lat: '43.70146', lng: '7.278485', title: 'Musée d\'Art Moderne et d\'Art Contemporain', address: 'Place Yves Klein, 06364 Nice', author: userOp, background_picture: bg_mamac)


            }
            catch (e) {
                e.printStackTrace()
                println 'erreur creation pois/parcours'
            }


        }
    }


    def destroy = {

    }
}
