import net.okjsp.MainService
import org.quartz.JobExecutionContext
import org.quartz.TriggerKey

/**
 * Created by langerhans on 2014. 9. 5..
 */
class MainArticlesJob {

    def grailsCacheAdminService

    static triggers = {
        simple name: 'choiceArticleCacheTrigger', startDelay: 10000, repeatInterval: 60000
        simple name: 'qnaArticlesCacheTrigger', startDelay: 20000, repeatInterval: 60000
        simple name: 'techArticlesCacheTrigger', startDelay: 30000, repeatInterval: 60000
        simple name: 'communityArticlesCacheTrigger', startDelay: 40000, repeatInterval: 60000
        simple name: 'columnsArticlesCacheTrigger', startDelay: 50000, repeatInterval: 60000
    }

    void execute(JobExecutionContext context){

        def triggerName = context.trigger.key.toString().tokenize('.')?.get(1)

        switch (triggerName) {
            case "choiceArticleCacheTrigger":
                grailsCacheAdminService.clearCache("choiceArticlesCache")
                break
            case "qnaArticlesCacheTrigger":
                grailsCacheAdminService.clearCache("qnaArticlesCache")
                break
            case "techArticlesCacheTrigger":
                grailsCacheAdminService.clearCache("techArticlesCache")
                break
            case "communityArticlesCacheTrigger":
                grailsCacheAdminService.clearCache("communityArticlesCache")
                break
            case "columnsArticlesCacheTrigger":
                grailsCacheAdminService.clearCache("columnsArticlesCache")
                break
        }
    }
}
