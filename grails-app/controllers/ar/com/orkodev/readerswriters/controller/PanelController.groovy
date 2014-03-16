package ar.com.orkodev.readerswriters.controller

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured("ROLE_US")
class PanelController extends BaseController {

    def tellingLikeService, followerService, tellingService;

    def dashboard() {
        render view:"dashboard"
    }

    def likeTelling(){
        def tellingList = tellingLikeService.findLikeTelling(5).collect{it ->
            [telling:[id: it.id, title: it.title,
                    author:[id: it.author.id, name: it.author.username]
                    ]
            ]
        }
        def result = [success:true,model:[elements:tellingList]]
        render result as JSON
    }

    def authorsFollowed(){
        def authorsList = followerService.findAuthorFollowed(5).collect{it ->
            [author: [id: it.id, name: it.username]]
        }
        def result = [success:true,model:[elements:authorsList]]
        render result as JSON
    }

    def ownTelling() {
        def tellingList = tellingService.findCurrentUserTelling(5).collect{it ->
            [telling:[id: it.id, title: it.title]]
        }
        def result = [success:true,model:[elements:tellingList]]
        render result as JSON
    }
}
