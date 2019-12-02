## Writing tests for Redux

### Action Creators

Trong Redux thì action creator là những hàm trả về plain object. Khi test action creator thì chúng ta muốn check xem 1 action creator cụ thể có được gọi không và action đúng có được trả về không.

Ví dụ với action này

```javascript
export function addTodo(text) {
  return {
    type: 'ADD_TODO',
    text
  }
}
```

thì có thể được test như dưới đây:

```javascript
import * as actions from '../../actions/TodoActions'
import * as types from '../../constants/ActionTypes'

describe('actions', () => {
  it('should create an action to add a todo', () => {
    const text = 'Finish docs'
    const expectedAction = {
      type: types.ADD_TODO,
      text
    }
    expect(actions.addTodo(text)).toEqual(expectedAction)
  })
})
```

### Async Action Creators

Với các action creator bất đồng bộ (tạo bởi Redux Thunk hoặc các loại middleware khác), tốt nhất là chúng ta nên mock cả Redux store để test. Bạn có thể sử dụng middleware để mock store bằng [redux-mock-store](https://github.com/arnaudbenard/redux-mock-store). Bạn cũng có thể dùng [fetch-mock](http://www.wheresrhys.co.uk/fetch-mock/) để mock HTTP request.

Ví dụ

```javascript
import 'cross-fetch/polyfill'

function fetchTodosRequest() {
  return {
    type: FETCH_TODOS_REQUEST
  }
}

function fetchTodosSuccess(body) {
  return {
    type: FETCH_TODOS_SUCCESS,
    body
  }
}

function fetchTodosFailure(ex) {
  return {
    type: FETCH_TODOS_FAILURE,
    ex
  }
}

export function fetchTodos() {
  return dispatch => {
    dispatch(fetchTodosRequest())
    return fetch('http://example.com/todos')
      .then(res => res.json())
      .then(body => dispatch(fetchTodosSuccess(body)))
      .catch(ex => dispatch(fetchTodosFailure(ex)))
  }
}
```

có thể được test như sau:

```javascript
import configureMockStore from 'redux-mock-store'
import thunk from 'redux-thunk'
import * as actions from '../../actions/TodoActions'
import * as types from '../../constants/ActionTypes'
import fetchMock from 'fetch-mock'
import expect from 'expect' // You can use any testing library

const middlewares = [thunk]
const mockStore = configureMockStore(middlewares)

describe('async actions', () => {
  afterEach(() => {
    fetchMock.restore()
  })

  it('creates FETCH_TODOS_SUCCESS when fetching todos has been done', () => {
    fetchMock.getOnce('/todos', {
      body: { todos: ['do something'] },
      headers: { 'content-type': 'application/json' }
    })

    const expectedActions = [
      { type: types.FETCH_TODOS_REQUEST },
      { type: types.FETCH_TODOS_SUCCESS, body: { todos: ['do something'] } }
    ]
    const store = mockStore({ todos: [] })

    return store.dispatch(actions.fetchTodos()).then(() => {
      // return of async actions
      expect(store.getActions()).toEqual(expectedActions)
    })
  })
})
```

### Reducers

Một reducer phải trả về state mới khi xử lý action cho state cũ, và đó là biểu hiện mà chúng ta sẽ test.

Ví dụ

```javascript
import { ADD_TODO } from '../constants/ActionTypes'

const initialState = [
  {
    text: 'Use Redux',
    completed: false,
    id: 0
  }
]

export default function todos(state = initialState, action) {
  switch (action.type) {
    case ADD_TODO:
      return [
        {
          id: state.reduce((maxId, todo) => Math.max(todo.id, maxId), -1) + 1,
          completed: false,
          text: action.text
        },
        ...state
      ]

    default:
      return state
  }
}
```

có thể được test như dưới đây:

```javascript
import reducer from '../../structuring-reducers/todos'
import * as types from '../../constants/ActionTypes'

describe('todos reducer', () => {
  it('should return the initial state', () => {
    expect(reducer(undefined, {})).toEqual([
      {
        text: 'Use Redux',
        completed: false,
        id: 0
      }
    ])
  })

  it('should handle ADD_TODO', () => {
    expect(
      reducer([], {
        type: types.ADD_TODO,
        text: 'Run the tests'
      })
    ).toEqual([
      {
        text: 'Run the tests',
        completed: false,
        id: 0
      }
    ])

    expect(
      reducer(
        [
          {
            text: 'Use Redux',
            completed: false,
            id: 0
          }
        ],
        {
          type: types.ADD_TODO,
          text: 'Run the tests'
        }
      )
    ).toEqual([
      {
        text: 'Run the tests',
        completed: false,
        id: 1
      },
      {
        text: 'Use Redux',
        completed: false,
        id: 0
      }
    ])
  })
})
```

## Middleware testing

### Saga

Saga là một trong những middleware của Redux với khả năng làm cho side effect của app (như các tác vụ bất đồng bộ như data fetching) trở nên dễ quản lý, hiệu quả hơn để chạy và test cũng như để xử lý lỗi.

Có 2 cách chính để test Saga: test các generator function từng bước một hoặc chạy hết saga và assert các side effect.

#### Testing the Saga Generator Function

Giả sử chúng ta có action như dưới đây:

```javascript
const CHOOSE_COLOR = 'CHOOSE_COLOR';
const CHANGE_UI = 'CHANGE_UI';

const chooseColor = (color) => ({
  type: CHOOSE_COLOR,
  payload: {
    color,
  },
});

const changeUI = (color) => ({
  type: CHANGE_UI,
  payload: {
    color,
  },
});
```

Chúng ta cần test saga dưới đây:

```javascript
function* changeColorSaga() {
  const action = yield take(CHOOSE_COLOR);
  yield put(changeUI(action.payload.color));
}
```

Bởi vì Saga luôn `yield` một `Effect`, và những `Effect` đó chỉ có những factory function cơ bản (như `put`, `take`), chúng ta có thể test bằng cách kiểm tra `Effect` sau khi `yield` có giống với `Effect` mà chúng ta mong đợi hay không. Để lấy được value đầu tiên `yield` bởi một saga, chúng ta chỉ cần gọi `next().value`:

```javascript
 const gen = changeColorSaga();

  assert.deepEqual(
    gen.next().value,
    take(CHOOSE_COLOR),
    'it should wait for a user to choose a color'
  );
```

A value must then be returned to assign to the action constant, which is used for the argument to the put effect:

Một giá trị cần phải được trả về để assign cho constant action để truyền vào `put` effect:

```javascript
 const color = 'red';
  assert.deepEqual(
    gen.next(chooseColor(color)).value,
    put(changeUI(color)),
    'it should dispatch an action to change the ui'
  );
```

Bởi vì không còn `yield` nào nữa, lần tới mà `next()` được gọi tới thì generator sẽ hoàn thành:

```javascript
assert.deepEqual(
    gen.next().done,
    true,
    'it should be done'
  );
```

#### Branching Saga

Đôi khi saga của bạn sẽ có những kết quả khác nhau. Để test các branch khác nhau mà không cần phải lặp lại những step dẫn đến nó thì bạn có thể sử dụng hàm utility `cloneableGenerator`.

Lần này chúng ta sẽ thêm 2 action mới, `CHOOSE_NUMBER` và `DO_STUFF`, với action creator tương ứng:

```javascript
const CHOOSE_NUMBER = 'CHOOSE_NUMBER';
const DO_STUFF = 'DO_STUFF';

const chooseNumber = (number) => ({
  type: CHOOSE_NUMBER,
  payload: {
    number,
  },
});

const doStuff = () => ({
  type: DO_STUFF,
});
```

Giờ thì saga sẽ put 2 action `DO_STUFF` trước khi chờ action `CHOOSE_NUMBER` và sau đó sẽ put một trong 2 action `changeUI('red')` và `changeUI('blue')` tùy thuộc vào số được chọn là số lẻ hay số chẵn.

```javascript
function* doStuffThenChangeColor() {
  yield put(doStuff());
  yield put(doStuff());
  const action = yield take(CHOOSE_NUMBER);
  if (action.payload.number % 2 === 0) {
    yield put(changeUI('red'));
  } else {
    yield put(changeUI('blue'));
  }
}
```

Chúng ta có thể test như sau:

```javascript
import { put, take } from 'redux-saga/effects';
import { cloneableGenerator } from '@redux-saga/testing-utils';

test('doStuffThenChangeColor', assert => {
  const gen = cloneableGenerator(doStuffThenChangeColor)();
  gen.next(); // DO_STUFF
  gen.next(); // DO_STUFF
  gen.next(); // CHOOSE_NUMBER

  assert.test('user choose an even number', a => {
    // cloning the generator before sending data
    const clone = gen.clone();
    a.deepEqual(
      clone.next(chooseNumber(2)).value,
      put(changeUI('red')),
      'should change the color to red'
    );

    a.equal(
      clone.next().done,
      true,
      'it should be done'
    );

    a.end();
  });

  assert.test('user choose an odd number', a => {
    const clone = gen.clone();
    a.deepEqual(
      clone.next(chooseNumber(3)).value,
      put(changeUI('blue')),
      'should change the color to blue'
    );

    a.equal(
      clone.next().done,
      true,
      'it should be done'
    );

    a.end();
  });
});
```

#### Testing the full Saga

Mặc dù nó có thể có ích khi bạn test từng bước của một saga, trong thực tế thì chạy cả saga và assert các effect mong đợi sẽ thích hợp hơn.

Giả sử chúng ta có một saga cơ bản dùng để gọi 1 API:

```javascript
function* callApi(url) {
  const someValue = yield select(somethingFromState);
  try {
    const result = yield call(myApi, url, someValue);
    yield put(success(result.json()));
    return result.status;
  } catch (e) {
    yield put(error(e));
    return -1;
  }
}
```

Chúng ta có thể chạy saga với giá trị mock:

```javascript
const dispatched = [];

const saga = runSaga({
  dispatch: (action) => dispatched.push(action),
  getState: () => ({ value: 'test' }),
}, callApi, 'http://url');
```

Sau đó chúng ta có thể test bằng cách assert các action được dispatch

```javascript
import sinon from 'sinon';
import * as api from './api';

test('callApi', async (assert) => {
  const dispatched = [];
  sinon.stub(api, 'myApi').callsFake(() => ({
    json: () => ({
      some: 'value'
    })
  }));
  const url = 'http://url';
  const result = await runSaga({
    dispatch: (action) => dispatched.push(action),
    getState: () => ({ state: 'test' }),
  }, callApi, url).toPromise();

  assert.true(myApi.calledWith(url, somethingFromState({ state: 'test' })));
  assert.deepEqual(dispatched, [success({ some: 'value' })]);
});
```

Ngoài ra, các bạn có thể tham khảo thêm một số thư viện hỗ trợ việc test saga tại [đây](https://redux-saga.js.org/docs/advanced/Testing.html).