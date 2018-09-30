import net.okjsp.Article
import net.okjsp.Content
import net.okjsp.ContentType
import net.okjsp.ContentVote
import net.okjsp.NotificationService

import javax.transaction.Transactional

/**
 * Created by langerhans on 2014. 9. 25..
 */
class CleanNoteJob {

    def concurrent = false

    static triggers = {
        cron name: 'cleanNoteTrigger', cronExpression: '0 0 0 * * ?'
    }

    @Transactional
    void execute(){

        def diff = new Date() - 30

        Content.where {
            and {
                eq('type', ContentType.NOTE)
                eq('article.id', 494529L)
                lt('dateCreated', diff)
            }
        }.deleteAll()
    }
}
