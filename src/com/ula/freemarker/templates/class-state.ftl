[#ftl]
// --------------------------------------------------------------------------------
// Generated code, do not edit
// --------------------------------------------------------------------------------

package ${package};

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class ${name} {

  

  // --------------------------------------------------------------------------------
  
    public enum StateType {
    [#list state as currentState]
    ${currentState.labelState.name}, //
    [/#list]
  }

 
  // --------------------------------------------------------------------------------
	 public enum EventType {
    [#list eventSM as currentEvent]
    ${currentEvent.name}, //
    [/#list]
  }


  // --------------------------------------------------------------------------------

   	protected StateType state = StateType.${initial.name};

  // --------------------------------------------------------------------------------

  protected List<EventType> eventQueue = new ArrayList<EventType>();

  protected boolean firing;

  // --------------------------------------------------------------------------------
  
  //Funcion de Transicion
    public synchronized void fireEvent(EventType event, //
      Map<String, Object> ctx, Map<String, Object> par) {

    if (checkPreFireEvent(event, ctx, par)) {
      internalFireEvent(event, ctx, par);
    }
  }
    // --------------------------------------------------------------------------------
   
   public StateType getCurrentState(){
   	return state;   
   }
          
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
      [#list state as currentState]
      case ${currentState.labelState.name} :
        state${currentState.labelState.name}(event, ctx, par);
        break;
      [/#list]
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

  protected boolean checkPreFireEvent(EventType event, //
      Map<String, Object> ctx, Map<String, Object> par) {

    return true;
  }

  [#list state as currentState]
  // --------------------------------------------------------------------------------

  private void state${currentState.labelState.name}(EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    switch (event) {
      [#list eventSM as currentEvent]
      	[#assign eventFound=false]
        [#list currentState.arc as currentArc]
          [#if currentArc.eventArc.name == currentEvent.name]
          	[#assign eventFound=true]
      case ${currentArc.eventArc.name} :
        stateChangeMsg(StateType.${currentArc.target.state.labelState.name}, event, ctx, par);

        extStateChange(this.state, StateType.${currentArc.target.state.labelState.name}, event, ctx, par);
        evtStateChange(this.state, StateType.${currentArc.target.state.labelState.name}, event, ctx, par);

        ${currentState.labelState.name}_${currentArc.eventArc.name}_${currentArc.target.state.labelState.name}(ctx, par);

        entStateChange(this.state, StateType.${currentArc.target.state.labelState.name}, event, ctx, par);

        stateChangeSet(StateType.${currentArc.target.state.labelState.name}, event, ctx, par);
        break;
          [/#if]
        [/#list]
        [#if eventFound == false]
      case ${currentEvent.name} :
        throwIllegalState(state, event);
        break;
        [/#if]
      [/#list]
      default :
        throwDefaultState(state, event);
        break;
    }
  }

  [/#list]
  [#list state as currentState]
  // --------------------------------------------------------------------------------

  protected void ent_${currentState.labelState.name}(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  protected void ext_${currentState.labelState.name}(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  [/#list]
  [#list eventSM as currentEvent]
  // --------------------------------------------------------------------------------

  protected void evt_${currentEvent.name}(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

  [/#list]
  [#list state as currentState]
      [#list currentState.arc as currentArc]
  // --------------------------------------------------------------------------------

  protected void ${currentState.labelState.name}_${currentArc.eventArc.name}_${currentArc.target.state.labelState.name}(Map<String, Object> ctx, Map<String, Object> par) {
    // Empty
  }

      [/#list]
  [/#list]
  // --------------------------------------------------------------------------------

  private void extStateChange(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {

    switch (currState) {
      [#list state as currentState]
      case ${currentState.labelState.name} :
        ext_${currentState.labelState.name}(currState, nextState, event, ctx, par);
        break;
      [/#list]
      default :
        throwDefaultState(currState, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void entStateChange(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {

    switch (nextState) {
      [#list state as currentState]
      case ${currentState.labelState.name} :
        ent_${currentState.labelState.name}(currState, nextState, event, ctx, par);
        break;
      [/#list]
      default :
        throwDefaultState(currState, event);
        break;
    }
  }

  // --------------------------------------------------------------------------------

  private void evtStateChange(StateType currState, StateType nextState, //
      EventType event, Map<String, Object> ctx, Map<String, Object> par) {

    switch (event) {
      [#list eventSM as currentEvent]
      case ${currentEvent.name} :
        evt_${currentEvent.name}(currState, nextState, event, ctx, par);
        break;
      [/#list]
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
