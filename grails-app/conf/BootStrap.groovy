import ar.com.orkodev.readerswriters.domain.NarrativeGenre
import ar.com.orkodev.readerswriters.domain.Role
import ar.com.orkodev.readerswriters.domain.TellingType
import ar.com.orkodev.readerswriters.domain.User
import ar.com.orkodev.readerswriters.domain.UserRole

class BootStrap {

    def init = { servletContext ->
        //Creo los roles en caso de no existir y además creo el usuario ADM
        //Sólo se tiene los roles ROLE_US y ROLE_ADM
        def roleAdm = Role.findByAuthority(Role.ROLE_ADM)
        if (!roleAdm){
            roleAdm = new Role(authority: Role.ROLE_ADM).save(flush: true,failOnError: true)
        }
        Role.findOrSaveByAuthority(Role.ROLE_US)
        def superUser = User.findByUsername("orkosuperuser@reader.com")
        if (!superUser){
            superUser = new User(username:"orko@reader.com",password:"nano2403").save(flush:true,failOnError: true)
            UserRole.create(superUser,roleAdm,true)
        }

        //creo tipos y géneros
        new TellingType(name: "cuento").save(flush: true,failOnError:  true)
        new TellingType(name: "novela").save(flush: true,failOnError: true)
        new TellingType(name: "mito").save(flush: true,failOnError: true)
        new TellingType(name: "leyenda").save(flush: true,failOnError: true)
        new TellingType(name: "fábula").save(flush: true,failOnError: true)

        //géneros
        new NarrativeGenre(name: "Terror").save(flush: true,failOnError: true)
        def padre1 = new NarrativeGenre(name: "Policial").save(flush: true,failOnError: true)
        new NarrativeGenre(name: "Romántico").save(flush: true,failOnError: true)

        new NarrativeGenre(name: "Suspenso",parent: padre1).save(flush: true,failOnError: true)
        new NarrativeGenre(name: "Detective",parent: padre1).save(flush: true,failOnError: true)

    }
    def destroy = {
    }
}
