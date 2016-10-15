// --------------------------------------------------------------------------------
// Generated code, do not edit
// --------------------------------------------------------------------------------

package com.ula.test.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class StateMachine {

  

  // --------------------------------------------------------------------------------

  public enum EventType {
    evento1, //
    evento2, //
    evento3, //
    evento4, //
    evento5, //
  }

  // --------------------------------------------------------------------------------

  public enum StateType {
    E0, //
    E1, //
    E2, //
    E3, //
  }

  // --------------------------------------------------------------------------------

   	protected StateType state = StateType.E2;

  // --------------------------------------------------------------------------------

  protected List<EventType> eventQueue = new ArrayList<EventType>();

  protected boolean firing;

  // --------------------------------------------------------------------------------

  protected void internalFireEvent(EventType event, //
      Map<String, Object> ctx, Map<String, Object> par) {

    if (firing) {
      eventQueue.add(event); // TODO: add par to queue
      return;
    }

    firing = true;
    
    System.err.println("firing " + //
        event + " event, in state " + state);

    switch (state) {
      case E0 :
        stateE0(event, ctx, par);
        break;
      case E1 :
        stateE1(event, ctx, par);
        break;
      case E2 :
        stateE2(event, ctx, par);
        break;
      case E3 :
        stateE3(event, ctx, par);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }

    firing = false;

    if (!eventQueue.isEmpty()) {
      EventType eventType = eventQueue.remove(0);
      fireEvent(eventType, ctx, null);
    }
  }

  // --------------------------------------------------------------------------------

  public synchronized void fireEvent(EventType event, //
      Map<String, Object> ctx, Map<String, Object> par) {

    if (checkPreFireEvent(event, ctx, par)) {
      internalFireEvent(event, ctx, par);
    }
  }

  // --------------------------------------------------------------------------------

  protected boolean checkPreFireEvent(EventType event, //
      Map<String, Object> ctx, Map<String, Object> par) {

    return true;
  }

  // --------------------------------------------------------------------------------

  private void stateE0(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      case evento1 :
    	  
    	  // cxt contexto (partida). par tabla hash que lleva valores 
        stateChangeMsg(StateType.E1, event, ctx, par);

        extStateChange(this.state, StateType.E1, event, ctx, par);
        evtStateChange(this.state, StateType.E1, event, ctx, par);

        E0_evento1_E1(ctx, par);

        entStateChange(this.state, StateType.E1, event, ctx, par);

        stateChangeSet(StateType.E1, event, ctx, par);
        break;
      case evento2 :
        stateChangeMsg(StateType.E3, event, ctx, par);

        extStateChange(this.state, StateType.E3, event, ctx, par);
        evtStateChange(this.state, StateType.E3, event, ctx, par);

        E0_evento2_E3(ctx, par);

        entStateChange(this.state, StateType.E3, event, ctx, par);

        stateChangeSet(StateType.E3, event, ctx, par);
        break;
      case evento3 :
        throwIllegalState(state, event);
        break;
      case evento4 :
        throwIllegalState(state, event);
        break;
      case evento5 :
        throwIllegalState(state, event);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateE1(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      case evento1 :
        throwIllegalState(state, event);
        break;
      case evento2 :
        throwIllegalState(state, event);
        break;
      case evento3 :
        throwIllegalState(state, event);
        break;
      case evento4 :
        throwIllegalState(state, event);
        break;
      case evento5 :
        throwIllegalState(state, event);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateE2(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      case evento1 :
        throwIllegalState(state, event);
        break;
      case evento2 :
        throwIllegalState(state, event);
        break;
      case evento3 :
        throwIllegalState(state, event);
        break;
      case evento4 :
        throwIllegalState(state, event);
        break;
      case evento5 :
        stateChangeMsg(StateType.E0, event, ctx, par);

        extStateChange(this.state, StateType.E0, event, ctx, par);
        evtStateChange(this.state, StateType.E0, event, ctx, par);

        E2_evento5_E0(ctx, par);

        entStateChange(this.state, StateType.E0, event, ctx, par);

        stateChangeSet(StateType.E0, event, ctx, par);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateE3(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      case evento1 :
        throwIllegalState(state, event);
        break;
      case evento2 :
        throwIllegalState(state, event);
        break;
      case evento3 :
        stateChangeMsg(StateType.E1, event, ctx, par);

        extStateChange(this.state, StateType.E1, event, ctx, par);
        evtStateChange(this.state, StateType.E1, event, ctx, par);

        E3_evento3_E1(ctx, par);

        entStateChange(this.state, StateType.E1, event, ctx, par);

        stateChangeSet(StateType.E1, event, ctx, par);
        break;
      case evento4 :
        stateChangeMsg(StateType.E2, event, ctx, par);

        extStateChange(this.state, StateType.E2, event, ctx, par);
        evtStateChange(this.state, StateType.E2, event, ctx, par);

        E3_evento4_E2(ctx, par);

        entStateChange(this.state, StateType.E2, event, ctx, par);

        stateChangeSet(StateType.E2, event, ctx, par);
        break;
      case evento5 :
        throwIllegalState(state, event);
        break;
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  protected void ent_E0(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_E0(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void ent_E1(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_E1(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void ent_E2(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_E2(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void ent_E3(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_E3(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void evt_evento1(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void evt_evento2(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void evt_evento3(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void evt_evento4(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void evt_evento5(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void E0_evento1_E1(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void E0_evento2_E3(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void E2_evento5_E0(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void E3_evento3_E1(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  protected void E3_evento4_E2(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  // --------------------------------------------------------------------------------

  private void extStateChange(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {

    switch (currState) {
      case E0 :
        ext_E0(currState, nextState, event, ctx, par);
        break;
      case E1 :
        ext_E1(currState, nextState, event, ctx, par);
        break;
      case E2 :
        ext_E2(currState, nextState, event, ctx, par);
        break;
      case E3 :
        ext_E3(currState, nextState, event, ctx, par);
        break;
      default :
        throwDefaultState(currState, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void entStateChange(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {

    switch (nextState) {
      case E0 :
        ent_E0(currState, nextState, event, ctx, par);
        break;
      case E1 :
        ent_E1(currState, nextState, event, ctx, par);
        break;
      case E2 :
        ent_E2(currState, nextState, event, ctx, par);
        break;
      case E3 :
        ent_E3(currState, nextState, event, ctx, par);
        break;
      default :
        throwDefaultState(currState, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void evtStateChange(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {

    switch (event) {
      case evento1 :
        evt_evento1(currState, nextState, event, ctx, par);
        break;
      case evento2 :
        evt_evento2(currState, nextState, event, ctx, par);
        break;
      case evento3 :
        evt_evento3(currState, nextState, event, ctx, par);
        break;
      case evento4 :
        evt_evento4(currState, nextState, event, ctx, par);
        break;
      case evento5 :
        evt_evento5(currState, nextState, event, ctx, par);
        break;
      default :
        throwDefaultState(currState, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void stateChangeMsg(StateType state, EventType event, //
      Map<String, Object> ctx, Map<String, Object> par) {

    System.err.println( //
        "Curr state is: " + this.state + //
            "; event is: " + event + "; next state is: " + state);
  }

  private void stateChangeSet(StateType state, EventType event, //
      Map<String, Object> ctx, Map<String, Object> par) {

    this.state = state;
  }

  // --------------------------------------------------------------------------------

  protected void throwIllegalState(StateType state, EventType event) {
    System.err.println( //
        "IllegalStateException: " + state + " for event " + event);

    throw new IllegalStateException( //
        /**/state + " for event " + event);
  }

  protected void throwDefaultState(StateType state, EventType event) {
    String msg = "default : this is for sure a bug in the state machine for state " + //
        state + " and event " + event;

    System.err.println(msg);

    throw new RuntimeException(msg);
  }
}
