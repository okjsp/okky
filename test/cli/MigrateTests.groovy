import grails.test.AbstractCliTestCase

class MigrateTests extends AbstractCliTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testMigrate() {

        execute(["migrate"])

        assertEquals 0, waitForProcess()
        verifyHeader()
    }
}
