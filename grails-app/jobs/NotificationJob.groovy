import grails.transaction.Transactional
import net.okjsp.Content
import net.okjsp.ContentType
import net.okjsp.ContentVote
import net.okjsp.NotificationService

/**
 * Created by langerhans on 2014. 9. 25..
 */
class NotificationJob {

    NotificationService notificationService
    static Date lastSend = new Date()

    def concurrent = false

    static triggers = {
        simple name: 'notificationTrigger', repeatInterval: 60000
    }

    void execute(){

        Date now = new Date()

        println "NotificationJob excuted by : ${lastSend} / now : ${now}"

        def notes = Content.findAll {
            and {
                eq('type', ContentType.NOTE)
                ge('dateCreated', lastSend)
            }
        }

        def votes = ContentVote.findAll {
            and {
                gt('point', 0)
                ge('dateCreated', lastSend)
            }
        }


        println "NotificationJob Notes Count ${notes.size()}"
        println "NotificationJob Votes Count ${votes.size()}"

        notes.each { note ->
            notificationService.createFromNote(note)
        }

        votes.each { vote ->
            notificationService.createFromAccent(vote)
        }

        println "NotificationJob excution time ${new Date().time-now.time}ms"

        lastSend = now
    }
}
