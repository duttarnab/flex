import {
  GET_PERMISSIONS,
  GET_PERMISSIONS_RESPONSE,
  GET_PERMISSION,
  GET_PERMISSION_RESPONSE,
  ADD_PERMISSION,
  ADD_PERMISSION_RESPONSE,
  EDIT_PERMISSION,
  EDIT_PERMISSION_RESPONSE,
  DELETE_PERMISSION,
  DELETE_PERMISSION_RESPONSE,
  SET_PERMISSION_ITEM,
  RESET,
} from '../actions/types'
import reducerRegistry from '../../../../app/redux/reducers/ReducerRegistry'
const INIT_STATE = {
  items: [],
  loading: true,
}
const reducerName = 'apiPermissionReducer'

export default function apiPermissionReducer(state = INIT_STATE, action) {
  switch (action.type) {
    case GET_PERMISSIONS:
      return {
        ...state,
        loading: true,
      }
    case GET_PERMISSIONS_RESPONSE:
      if (action.payload.data) {
        return {
          ...state,
          items: action.payload.data,
          loading: false,
        }
      } else {
        return handleDefault()
      }
    case GET_PERMISSION:
      return {
        ...state,
        loading: true,
      }
    case GET_PERMISSION_RESPONSE:
      if (action.payload.data) {
        return {
          ...state,
          items: action.payload.data,
          loading: false,
        }
      } else {
        return handleDefault()
      }
    case ADD_PERMISSION:
      return {
        ...state,
        loading: true,
      }
    case ADD_PERMISSION_RESPONSE:
      if (action.payload.data) {
        return {
          ...state,
          items: [...state.items, action.payload.data],
          loading: false,
        }
      } else {
        return handleDefault()
      }

    case EDIT_PERMISSION:
      return {
        ...state,
        loading: true,
      }
    case EDIT_PERMISSION_RESPONSE:
      if (action.payload.data) {
        return {
          ...state,
          items: [...state.items],
          loading: false,
        }
      } else {
        return handleDefault()
      }

    case DELETE_PERMISSION:
      return {
        ...state,
        loading: true,
      }
    case DELETE_PERMISSION_RESPONSE:
      if (action.payload.inum) {
        return {
          ...state,
          items: state.items.filter(
            (item) => item.inum !== action.payload.inum,
          ),
          loading: false,
        }
      } else {
        return handleDefault()
      }
    case SET_PERMISSION_ITEM:
      return {
        ...state,
        item: action.payload.item,
        loading: false,
      }
    case RESET:
      return {
        ...state,
        items: INIT_STATE.items,
        loading: INIT_STATE.loading,
      }
    default:
      return handleDefault()
  }

  function handleDefault() {
    return {
      ...state,
      loading: false,
    }
  }
}
reducerRegistry.register(reducerName, apiPermissionReducer)
