import backend.GarbageClass
import spock.lang.Specification


// Just for first step and testing purpose. Delete later

class GarbageClassTest extends Specification {
    def "just true"() {
        expect: 1 == 1
    }

    def "garbageFirst one"() {
        given:
            GarbageClass garbageClass = new GarbageClass()

        expect:
            garbageClass.garbageFirst(true) == 1
    }

    def "garbageFirst zero"() {
        given:
            GarbageClass garbageClass = new GarbageClass()

        expect:
            garbageClass.garbageFirst(false) == 0
    }
}
