package io.snice.gatling.gtp.engine

import io.gatling.commons.util.Clock
import io.gatling.core.action.Action
import io.gatling.core.session.Session
import io.gatling.core.stats.StatsEngine
import io.snice.codecs.codec.gtp.gtpc.v2.{Gtp2Request, Gtp2Response}
import io.snice.networking.gtp.Transaction

object ResponseProcessor {

  def apply(name: String, req: Gtp2Request, session: Session, statsEngine: StatsEngine, clock: Clock, next: Action): ResponseProcessor = {
    new ResponseProcessor(name, req, session, statsEngine, clock, next)
  }

}

/**
 * Responsible for processing any [[Gtp2Response]]s, run any user defined checks on that response
 * and to update the Gatling stats engine with the result.
 *
 * @param name
 * @param req
 * @param session
 * @param statsEngine
 * @param clock
 * @param next
 */
class ResponseProcessor(name: String,
                        req: Gtp2Request,
                        // checks: List[DiameterCheck], // will get to this
                        session: Session,
                        statsEngine: StatsEngine,
                        clock: Clock,
                        next: Action) {
  val start = clock.nowMillis

  def process(transaction: Transaction, response: Gtp2Response): Unit = {
    val responseStopTime = clock.nowMillis

    // TODO: implement checks...
    // val (checkedSession, checkError) = Check.check(answer, session, checks, null)
    // val newSession = if (checkError.isDefined) checkedSession.markAsFailed else checkedSession

    val message = None
    val responseCode = None
    statsEngine.logResponse(session, name, start, responseStopTime, session.status, responseCode, message)

    next ! session
  }
}