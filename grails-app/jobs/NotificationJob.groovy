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
        println "NotificationJob excution date ${now} / lastsent ${lastSend}"

        try {

            def notes = Content.findAll {
                and {
                    eq('type', ContentType.NOTE)
                    gt('dateCreated', lastSend)
                }
            }

            def votes = ContentVote.findAll {
                and {
                    gt('point', 0)
                    gt('dateCreated', lastSend)
                }
            }

            notes.each { note ->
                notificationService.createFromNote(note)
            }

            votes.each { vote ->
                notificationService.createFromAccent(vote)
            }
        } catch(Exception e) {

        }

        lastSend = now


//        println "NotificationJob excution time ${new Date().time-now.time}ms"
    }
}
